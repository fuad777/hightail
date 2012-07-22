/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hightail.parsers.task;

import java.util.ArrayList;
import org.hightail.Testcase;
import org.hightail.TestcaseSet;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.htmlparser.filters.LinkRegexFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.TagFindingVisitor;

/**
 *
 * @author robertrosolek
 */
public class CodeForcesTaskParser extends TaskParser {
    
    // extracts string from input or output node for a testcase
    String extractTextFromNode(Node node) {
        // TODO
        return null;
    }

    @Override
    public TestcaseSet parse(String URL) throws ParserException {
        
        URL = URL.trim();
        
        Parser parser = new Parser(URL);
        
        // get all div tags
        String[] tagsToVisit = {"div"};
        TagFindingVisitor visitor = new TagFindingVisitor(tagsToVisit);
        parser.visitAllNodesWith(visitor);
        Node[] divNodes = visitor.getTags(0);
        
        // filter tags for those with class "input" and create 
        // a list of input strings
        CssSelectorNodeFilter inputFilter = new CssSelectorNodeFilter("div.input");
        ArrayList<String> inputs = new ArrayList<String>();
        for (Node node: divNodes)
                if (inputFilter.accept(node)) {
                    String inputString = extractTextFromNode(node);
                    inputs.add(inputString);
                }
        
        // filter tags for those with class "output" and create
        // a list of output strings
        CssSelectorNodeFilter outputFilter = new CssSelectorNodeFilter("div.output");
        ArrayList<String> outputs = new ArrayList<String>();
        for (Node node: divNodes)
                if (outputFilter.accept(node)) {
                    String outputString = extractTextFromNode(node);
                    outputs.add(outputString);
                }
                
        if (inputs.size() != outputs.size())
                throw new ParserException();
        
        TestcaseSet result = new TestcaseSet();
        for (int i = 0; i < inputs.size(); ++i)
            result.add(new Testcase(inputs.get(i), outputs.get(i)));
       
        
        return result;
    }
    
}
