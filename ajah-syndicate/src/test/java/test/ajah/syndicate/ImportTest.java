/*
 *  Copyright 2012 Eric F. Savage, code@efsavage.com
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package test.ajah.syndicate;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.junit.Test;

import com.ajah.syndicate.opml.Opml;
import com.ajah.syndicate.opml.Outline;
import com.ajah.syndicate.rome.OpmlUtils;
import com.ajah.util.CollectionUtils;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SAXBuilder;

/**
 * Test parsing and importing an OPML file.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class ImportTest {

	private static final Logger log = Logger.getLogger(ImportTest.class.getName());

	/**
	 * Test parsing Scoble's gigantic OPML file.
	 * 
	 * @throws IllegalArgumentException
	 * @throws FeedException
	 * @throws JDOMException
	 * @throws IOException
	 */
	@Test
	public void testOpmlImport() throws IllegalArgumentException, FeedException, JDOMException, IOException {
		Document doc = new SAXBuilder(false).build(getClass().getResourceAsStream("/scoble.opml"));
		Opml opml = OpmlUtils.parse(doc);
		printOutlines(opml.getOutlines());
	}

	/**
	 * Print outlines out for humans to see.
	 * 
	 * @param outlines
	 *            The outlines to print.
	 */
	private void printOutlines(List<Outline> outlines) {
		if (CollectionUtils.isEmpty(outlines)) {
			return;
		}
		for (Outline outline : outlines) {
			String line = "";
			for (int i = 0; i < outline.getDepth(); i++) {
				line += "--";
			}
			line += outline.getTitle();
			log.fine(line);
			printOutlines(outline.getOutlines());
		}
	}

}
