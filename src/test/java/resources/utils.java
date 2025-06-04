package resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class utils {

	public static RequestSpecification req;
	
	public RequestSpecification requestSpecification() throws IOException
	{
	
		if(req==null)
		{
		PrintStream stream=new PrintStream(new FileOutputStream("logging.txt"));
		 req= new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl")).addQueryParam("key", "qaclick123")
				 .addFilter(RequestLoggingFilter.logRequestTo(stream))
				 .addFilter(ResponseLoggingFilter.logResponseTo(stream))
		.setContentType(ContentType.JSON)
		.build();
		 return req;
		}
		return req;
	}
	
	public String getGlobalValue(String key) throws IOException
	{
		Properties p =new Properties();
		FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"/src/test/java/resources/global.properties");
		p.load(fis);
		return p.getProperty(key);
		
	}
	
	public String getJsonPath(Response response, String key)
	{
		//System.out.println(response.toString());
		System.out.println(response.asString());
		JsonPath js=new JsonPath(response.asString());
		return js.get(key).toString();
	}
}
