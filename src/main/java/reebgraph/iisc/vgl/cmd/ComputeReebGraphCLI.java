/*
 *	Copyright (C) 2010 Visualization & Graphics Lab (VGL), Indian Institute of Science
 *
 *	This file is part of libRG, a library to compute Reeb graphs.
 *
 *	libRG is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU Lesser General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	libRG is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU Lesser General Public License for more details.
 *
 *	You should have received a copy of the GNU Lesser General Public License
 *	along with libRG.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Author(s):	Harish Doraiswamy
 *	Version	 :	1.0
 *
 *	Modified by : -- 
 *	Date : --
 *	Changes  : --
 */
package reebgraph.iisc.vgl.cmd;

import meshloader.iisc.vgl.external.loader.DataLoader;
import meshloader.iisc.vgl.external.loader.MeshLoader;
import reebgraph.iisc.vgl.reebgraph.ReebGraph;

import java.io.FileInputStream;
import java.util.Properties;


public class ComputeReebGraphCLI {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Properties p = new Properties();
			p.load(new FileInputStream("input.properties"));
			String loaderType = p.getProperty("loader");
			String ip = p.getProperty("inputFile").trim();
			String fn = p.getProperty("inputFunction").trim();
			if(!isInteger(fn)) {
				System.err.println("Input function should be a co-ordinate index (0 indicates given scalar function)");
				System.exit(1);
			}
			int granularity = Integer.parseInt(p.getProperty("granularity").trim());
			if(granularity != -1 && granularity < 1) {
				granularity = 1;
			}
			String op = null;
			try {
				 op = p.getProperty("output").trim();	
			} catch (Exception e) {
				op = null;
			}
			if(op != null && op.equalsIgnoreCase("")) {
				op = null;
			}
			
			long st, en;
			ReebGraph rg = new ReebGraph();	
			rg.setGranularity(granularity);
			st = System.currentTimeMillis();
			
			MeshLoader loader = DataLoader.getLoader(loaderType);
			loader.setInputFile(ip);
			rg.computeReebGraph(loader, fn);
			en = System.currentTimeMillis();
			rg.outputReebGraph(op);
			System.out.println("Total Time Taken : " + (en - st) + "ms");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Invalid input. Check the input.properties file");
			System.exit(0);
		}
	}

	private static boolean isInteger(String fn) {
		try {
			Integer.parseInt(fn.trim());
			return true;
		} catch(Exception e) {
			return false;
		}
	}
}


