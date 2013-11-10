package com.nicolatesser.fantasycpuinterpret;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FantasyCpuProgramParser {

	public List<String> parseProgram(String input) throws IOException {

		List<String> program = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(input));
		try {
			String line = br.readLine();
			program.add(line);
			while (line != null) {
				line = br.readLine();
				program.add(line);
			}
		} finally {
			br.close();
		}
		return program;
	}

}
