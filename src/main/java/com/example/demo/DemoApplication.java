package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class DemoApplication {

	static String [] files = {"589.txt", "590.txt","645.txt","695.txt","707.txt","804.txt"};
	static String [] KEY_WORDS = {"java", "database"};
	static String [] REGEXES = {"\\b" + KEY_WORDS[0] + "\\b", "\\b" + KEY_WORDS[1] + "|" + KEY_WORDS[1] + "s" + "\\b"};
	static Pattern [] PATTERNS = {Pattern.compile(REGEXES[0], Pattern.CASE_INSENSITIVE), Pattern.compile(REGEXES[1], Pattern.CASE_INSENSITIVE)};

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData() throws URISyntaxException, IOException {

		return (args) -> {

			for(int i=0; i<files.length; i++){
				Path path = Paths.get(getClass().getClassLoader()
						.getResource(files[i]).toURI());
				List<String> lines = Files.lines(path).collect(Collectors.toList());
				String match = "";
				for(String str: lines){
					if(str != null){
						if(str.toLowerCase().contains(KEY_WORDS[0]) && str.toLowerCase().contains(KEY_WORDS[1])){
							System.out.println("Description contains Java and database: " + files[i]);
							break;
						}else if(str.toLowerCase().contains(KEY_WORDS[0])){
							if(KEY_WORDS[1].equals(match)){
								System.out.println("Description contains Java and database:" + files[i]);
								break;
							}
							match = KEY_WORDS[0];
						}else if(str.toLowerCase().contains(KEY_WORDS[1])) {
							if (KEY_WORDS[0].equals(match)) {
								System.out.println("Description contains Java and database: " + files[i]);
								break;
							}
							match = KEY_WORDS[1];
						}
					}
				}
			}

			System.out.println("");

			for(int i=0; i<files.length; i++) {
				Path path = Paths.get(getClass().getClassLoader()
						.getResource(files[i]).toURI());
				List<String> lines = Files.lines(path).collect(Collectors.toList());
				String match = "";
				for (String str : lines) {
					if (str != null) {
						Matcher [] matchers = {PATTERNS[0].matcher(str), PATTERNS[1].matcher(str)};
						boolean [] founds = {matchers[0].find(), matchers[1].find()};
						if (founds[0] && founds[1]) {
							System.out.println("Description contains Java and database: " + files[i]);
							break;
						} else if (founds[0]) {
							if (KEY_WORDS[1].equals(match)) {
								System.out.println("Description contains Java and database:" + files[i]);
								break;
							}
							match = KEY_WORDS[0];
						} else if (founds[1]) {
							if (KEY_WORDS[0].equals(match)) {
								System.out.println("Description contains Java and database: " + files[i]);
								break;
							}
							match = KEY_WORDS[1];
						}
					}
				}
			}
		};
	}
}