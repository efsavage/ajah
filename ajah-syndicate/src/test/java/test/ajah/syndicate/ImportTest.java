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

import lombok.extern.java.Log;

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
@Log
public class ImportTest {

	/**
	 * Print outlines out for humans to see.
	 * 
	 * @param outlines
	 *            The outlines to print.
	 */
	private void printOutlines(final List<Outline> outlines) {
		if (CollectionUtils.isEmpty(outlines)) {
			return;
		}
		for (final Outline outline : outlines) {
			String line = "";
			for (int i = 0; i < outline.getDepth(); i++) {
				line += "--";
			}
			line += outline.getTitle();
			log.fine(line);
			printOutlines(outline.getOutlines());
		}
	}

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
		final Document doc = new SAXBuilder(false).build(getClass().getResourceAsStream("/opml/scoble.opml"));
		final Opml opml = OpmlUtils.parse(doc);
		printOutlines(opml.getOutlines());
	}

}
