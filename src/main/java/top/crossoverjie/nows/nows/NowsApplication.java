package top.crossoverjie.nows.nows;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import top.crossoverjie.nows.nows.filter.FilterProcessManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class NowsApplication implements CommandLineRunner{


	private static Logger logger = LoggerFactory.getLogger(NowsApplication.class);


	@Autowired
	private FilterProcessManager filterProcessManager ;

	public static void main(String[] args) throws IOException {
		SpringApplication.run(NowsApplication.class, args);

		String path = "/Users/chenjie/Downloads/test/" ;





		//long sum = 0 ;
		//for (String msg : collect) {
		//	sum = fileManager.process(msg);
		//}





	}

	@Override
	public void run(String... strings) throws Exception {

		String fileName = "/Users/chenjie/Downloads/test/SSM1.md";
		Stream<String> stringStream = Files.lines(Paths.get(fileName), StandardCharsets.UTF_8);

		List<String> collect = stringStream.collect(Collectors.toList());
		long count = 0 ;
		for (String s : collect) {
			count = filterProcessManager.process(s);

		}
		logger.info("sum={}",count);
	}
}
