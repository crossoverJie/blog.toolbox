package top.crossoverjie.nows.nows;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
public class NowsApplicationTests {

	@Test
	public void contextLoads() {

		String url = "https://gitee.com/crossoverJie/Java-Interview" ;
		String https = url.replaceAll("^((https|http|ftp|rtsp|mms)?:\\/\\/)[^\\s]+", "");
		System.out.println(https);
	}

	@Test
	public void recursiveFile(){
		getFile("/Users/chenjie/Downloads/test");
		System.out.println("recursiveCount="+recursiveCount);
	}

	private int recursiveCount = 0 ;

	public void getFile(String path){
		recursiveCount ++ ;
		File f = new File(path) ;

		File[] files = f.listFiles();
		for (File file : files) {
			if (file.isDirectory()){
				String path1 = file.getPath();
				getFile(path1);
			}else {

				String path1 = file.getPath();
				if (!path1.endsWith(".md")){
					continue;
				}
				System.out.println(path1);

			}
		}
	}


	@Test
	public void picMatch(){
		int count = 0;
		String str="![](https://ws3.sinaimg.cn/large/006tKfTcly1g0mh751dkxj30qt087752.jpg)";
		String pattern="https?://.+\\.(jpg|gif|png)";

		Pattern r=Pattern.compile(pattern);
		Matcher m=r.matcher(str);
		while( m.find() )
		{
			count++;
			System.out.println( "匹配项" + count+"：" + m.group() ); //group方法返回由以前匹配操作所匹配的输入子序列。
		}
		System.out.println( "匹配个数为"+count );
	}

}
