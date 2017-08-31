package enabler.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.JSONUtil;
import utils.LogUtil;

public class DBEnabler extends AJdbc{
	public DBEnabler() {
	}
	public void setAutoCommit(boolean autoCommit) throws SQLException{
		conn.setAutoCommit(autoCommit);
	}
	public static DBEnabler open(boolean autoCommit) throws Exception{
		DBEnabler enabler=new DBEnabler();
		enabler.prepare();
		enabler.setAutoCommit(autoCommit);
		return enabler;
	}
	public static DBEnabler open(String config,boolean autoCommit) throws Exception{
		if(config==null) return null;
		DBConfig configObj=(DBConfig) JSONUtil.parse(config,DBConfig.class);
		DBEnabler enabler=new DBEnabler();
		enabler.prepare(configObj);
		enabler.setAutoCommit(autoCommit);
		return enabler;
	}
	private Req trans(String src){
		return (Req) JSONUtil.parse(src, Req.class);
	}
//	private String getSql(String src){
//		Req req=trans(src);
//		String sql=req.sql;
//		Map<String,String> map=req.param;
//		Set<String> keySet=map.keySet();
//		for (String key : keySet) {
//			String value=map.get(key);
//			if(value==null){
//				sql=sql.replace("@"+key, "null");
//			}else{
//				sql=sql.replace("@"+key, "'"+value+"'");
//			}
//		}
//		return sql;
//	}
	public void commitAndClose() throws SQLException{
		 conn.commit();
		 conn=null;
	}
	public void rollbackAndClose() throws SQLException{
		 conn.rollback();
		 conn=null;
	}
	private String rs2str(ResultSet rs) throws SQLException{
		List<Map<String,String>> list=new ArrayList<>();
		ResultSetMetaData rsmd=rs.getMetaData();
		int colCount=rs.getMetaData().getColumnCount();
		while (rs.next()) {
			Map<String,String> map=new HashMap<>();
			for (int i = 1; i <= colCount; i++) {
				String columnName=rsmd.getColumnName(i);
				String value=rs.getString(i);
				map.put(columnName, value);
			}
			list.add(map);
		}
		return JSONUtil.stringify(list);
	}
	public String query(String src) throws SQLException{
		//构造参数
		Req req=trans(src);
		LogUtil.p("处理前的sql语句为",req.sql);
		String sql=req.getPrepareSql();
		LogUtil.p("预编译的sql语句为",sql);
		PreparedStatement prest =conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);   
		if(req.param!=null&&!req.param.isEmpty()){
			List<Object> param=req.getPrepareParam();
			LogUtil.p("待执行的参数为",param);
	    	for (int i = 1; i <= param.size(); i++) {
				Object obj=param.get(i-1);
				prest.setObject(i, obj);
			}
		}
		ResultSet rs=prest.executeQuery();
		String str=rs2str(rs);
		LogUtil.p("查询出的结果为",str);
		return str;
	}
	/**
	 * 只能反最新的一个id
	 * @param src
	 * @return
	 * @throws SQLException
	 */
	public String execute(String src) throws SQLException{
//		LogUtil.p("src",src);
		//带?的sql
		//构造参数
		Req req=trans(src);
		LogUtil.p("处理前的sql语句为",req.sql);
		String sql=req.getPrepareSql();
		LogUtil.p("预编译的sql语句为",sql);
		PreparedStatement prest =conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);   
		List<Integer> rsList=new ArrayList<>();
		if(req.param!=null&&!req.param.isEmpty()){
			List<Object> param=req.getPrepareParam();
			LogUtil.p("待执行的参数为",param);
	    	for (int i = 1; i <= param.size(); i++) {
				Object obj=param.get(i-1);
				prest.setObject(i, obj);
			}
	    	prest.addBatch();
		}else{
			List<List<Object>> params=req.getPrepareParams();
			LogUtil.p("待执行的批量参数为",params);
		    for (List<Object> param : params) {
		    	int size = param.size();
		    	for (int i = 1; i <= size; i++) {
					Object obj=param.get(i-1);
					prest.setObject(i, obj);
				}
		    	 prest.addBatch();
			}
		}
		//批处理+预编译
	    prest.executeBatch(); 
	    ResultSet results=prest.getGeneratedKeys();
	    int num=-1;
	    
	    while(results.next())
	    {
	    	num = results.getInt(1);
	    	rsList.add(num);
	    }
		return JSONUtil.stringify(rsList);
	}
	public static void main(String[] args) throws Exception {
		System.out.println("==");
		String config="{\"url\":\"jdbc:mysql://192.168.1.15:3306/yy?characterEncoding=utf-8&characterSetResults=utf-8&useUnicode=false\",\"user\":\"root\",\"password\":\"ymt15\",\"driver\":\"com.mysql.jdbc.Driver\"}";
		String src="{\"sql\":\"insert into jobinfo(posName,companyName,location,releaseTime,tagList,needPeopleYears,posDesc,contact,companyInfo,propertyPeopleIndustry) values(@posName,@companyName,@location,@releaseTime,@tagList,@needPeopleYears,@posDesc,@contact,@companyInfo,@propertyPeopleIndustry)\",\"params\":[{\"posName\":\"数据分析师\",\"companyName\":\"“前程无忧”51job.com（上海）\",\"location\":\"上海-浦东新区\",\"releaseTime\":\"08-24\",\"tagList\":\"周末双休 带薪年假 专业培训\",\"needPeopleYears\":\"2年经验 本科 招聘1人 08-24发布\",\"posDesc\":\"职位信息 职位描述： 岗位职责： 1、运用数据挖掘/统计学习的理论和方法，深入挖掘和分析用户行为、业务数据。 2、负责并参与方案讨论、技术调研及产品开发，负责相关文档的编写。 3、与团队内部以及需求部门进行有效沟通，跟进项目进度以及解决相关数据问题。 任职要求： 1、本科及以上学历，统计学、应用数学或计算机科学等相关专业背景优先考虑。 2、熟悉互联网行业，了解用户需求，懂用户体验。 3、熟悉数据挖掘、机器学习、人工智能技术，尤其是关联分析、分类预测、协同过滤、聚类分析、回归分析、时间序列分析等常用分析方法，2年以上有数据挖掘、数据产品开发等相关项目经验者优先考虑。 4、至少掌握一门编程语言，熟练使用常用算法和数据结构，有较强的实现能力，能熟练使用R语言、Python者优先考虑。 5、具备责任心和良好的团队协作精神，乐于沟通交流和分享，充满激情，乐于接受挑战。 职能类别： 系统分析员 关键字： 数据分析员 举报 分享\",\"contact\":\"联系方式 上班地址：张东路1387号3号楼 地图\",\"companyInfo\":\"公司信息 “前程无忧”(NASDAQ: JOBS) 是中国具有广泛影响力的人力资源服务供应商，在美国上市的中国人力资源服务企业。它运用了网络媒体及先进的移动端信息技术，加上经验丰富的专业顾问队伍，提供包括招聘猎头、培训测评和人事外包在内的全方位专业人力资源服务，现在全国25个城市设有服务机构，是国内领先的专业人力资源服务机构。 51job (Nasdaq: JOBS) is a leading human resource solutions provider in China, offering a broad array of services in the areas of recruitment solutions, training and assessment, and HR outsourcing and consulting services. Combining the strengths of traditional (print) and new (Internet) media, 51job delivers an integrated recruitment solution by leveraging technology and expertise with a large staff of experienced professionals. 51job serves hundreds of thousands of domestic and multinational corporate clients through 25 offices in Mainland China. 51job is the most influential HR services provider in China. In September 2004, 51job successfully completed its IPO on Nasdaq, and is the first publicly listed firm in the field of HR services in China today.\",\"propertyPeopleIndustry\":\"外资（非欧美）   |  500-1000人   |  互联网/电子商务,专业服务(咨询、人力资源、财会)\"},{\"posName\":\"python 高级工程师\",\"companyName\":\"上海网元电子商务有限公司\",\"location\":\"上海-静安区\",\"releaseTime\":\"08-25\",\"tagList\":\"五险一金 绩效奖金 餐饮补贴 年终奖金\",\"needPeopleYears\":\"2年经验 本科 招聘若干人 08-25发布 英语 熟练\",\"posDesc\":\"职位信息 职位描述： 1、负责开发、维护公司底层框架及应用； 2、负责对使用框架产品的团队进行培训和支持； 任职要求： 1、2年以上Python 编程经验，熟悉 Flask 框架 ； 2、熟悉 Mongodb，SqlAlchemy ； 3、积极上进，善于学习，具备良好的分析、解决问题的能力; 4、熟悉 linux; 5、热爱软件开发，良好的沟通能力和团队协作精神； 职能类别： 高级软件工程师 软件工程师 关键字： python 举报 分享\",\"contact\":\"联系方式 上班地址：静安区铜仁路258号金座9C 地图\",\"companyInfo\":\"公司信息       网元公司成立于2001年，网元公司拥有十多年的面向电子商务、分销、零售领域的商务及供应链开发和集成经验，网元为国际著名企业提供了基于商务/供应链解决方案的产品以及集成和支持服务。网元同时为国际零售/商务企业提供了一整套多租户/多语言的包含商务/供应链解决方案的SaaS系统。       网元公司为国际、国内客户量身打造全方位的电子商务解决方案。帮助他们在国内外成功实施网元电子商务ERP系统，从而在电商领域获得巨大成功。       网元公司与国际知名企业Demandware, Magento等电子商务平台结成战略联盟，为全球电商用户提供优质的电子商务综合服务和技术/集成服务。       目前公司有，总部及研发中心（上海），NetSDL日本（东京），NetSDL国际（波士顿） ，并已收购美国Stone Edge Technologies。\",\"propertyPeopleIndustry\":\"外资（欧美）   |  少于50人   |  计算机软件\"}]}";
//		String src="{\"sql\":\"insert into jobinfo(posName,companyName,location,releaseTime,tagList,needPeopleYears,posDesc,contact,companyInfo,propertyPeopleIndustry) values(@posName,@companyName,@location,@releaseTime,@tagList,@needPeopleYears,@posDesc,@contact,@companyInfo,@propertyPeopleIndustry)\",\"param\":{\"posName\":\"数据分析师\",\"companyName\":\"“前程无忧”51job.com（上海）\",\"location\":\"上海-浦东新区\",\"releaseTime\":\"08-24\",\"tagList\":\"周末双休 带薪年假 专业培训\",\"needPeopleYears\":\"2年经验 本科 招聘1人 08-24发布\",\"posDesc\":\"职位信息 职位描述： 岗位职责： 1、运用数据挖掘/统计学习的理论和方法，深入挖掘和分析用户行为、业务数据。 2、负责并参与方案讨论、技术调研及产品开发，负责相关文档的编写。 3、与团队内部以及需求部门进行有效沟通，跟进项目进度以及解决相关数据问题。 任职要求： 1、本科及以上学历，统计学、应用数学或计算机科学等相关专业背景优先考虑。 2、熟悉互联网行业，了解用户需求，懂用户体验。 3、熟悉数据挖掘、机器学习、人工智能技术，尤其是关联分析、分类预测、协同过滤、聚类分析、回归分析、时间序列分析等常用分析方法，2年以上有数据挖掘、数据产品开发等相关项目经验者优先考虑。 4、至少掌握一门编程语言，熟练使用常用算法和数据结构，有较强的实现能力，能熟练使用R语言、Python者优先考虑。 5、具备责任心和良好的团队协作精神，乐于沟通交流和分享，充满激情，乐于接受挑战。 职能类别： 系统分析员 关键字： 数据分析员 举报 分享\",\"contact\":\"联系方式 上班地址：张东路1387号3号楼 地图\",\"companyInfo\":\"公司信息 “前程无忧”(NASDAQ: JOBS) 是中国具有广泛影响力的人力资源服务供应商，在美国上市的中国人力资源服务企业。它运用了网络媒体及先进的移动端信息技术，加上经验丰富的专业顾问队伍，提供包括招聘猎头、培训测评和人事外包在内的全方位专业人力资源服务，现在全国25个城市设有服务机构，是国内领先的专业人力资源服务机构。 51job (Nasdaq: JOBS) is a leading human resource solutions provider in China, offering a broad array of services in the areas of recruitment solutions, training and assessment, and HR outsourcing and consulting services. Combining the strengths of traditional (print) and new (Internet) media, 51job delivers an integrated recruitment solution by leveraging technology and expertise with a large staff of experienced professionals. 51job serves hundreds of thousands of domestic and multinational corporate clients through 25 offices in Mainland China. 51job is the most influential HR services provider in China. In September 2004, 51job successfully completed its IPO on Nasdaq, and is the first publicly listed firm in the field of HR services in China today.\",\"propertyPeopleIndustry\":\"外资（非欧美）   |  500-1000人   |  互联网/电子商务,专业服务(咨询、人力资源、财会)\"}}";
		DBEnabler sqlExecute = DBEnabler.open(config, false);
		try {
			String idList = sqlExecute.execute(src);
			LogUtil.p("idList",idList);
			sqlExecute.commitAndClose();
		} catch (Exception e) {
			e.printStackTrace();
			sqlExecute.rollbackAndClose();
		}
	}
}
