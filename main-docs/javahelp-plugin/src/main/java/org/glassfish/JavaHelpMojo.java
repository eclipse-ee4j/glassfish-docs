/*
 	Copyright (c) 2019 Oracle and/or its affiliates. All rights reserved.
	
	This program and the accompanying materials are made available under the
	terms of the Eclipse Public License v. 2.0, which is available at
	http://www.eclipse.org/legal/epl-2.0.
	
	This Source Code may also be made available under the following Secondary
	Licenses when the conditions for such availability set forth in the
	Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
	version 2 with the GNU Classpath Exception, which is available at
	https://www.gnu.org/software/classpath/license.html.
	
	SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
*/
package org.glassfish.javahelp;

import com.sun.java.help.search.Indexer;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.util.ArrayList;

/**
 * Invokes Javahelp indexer
 * @goal jhindex 
 * @phase generate-resources
 */
public class JavaHelpMojo extends AbstractMojo
{
    private static Indexer indexer = new Indexer();

    /**
     * The directory generating JavaHelp Indexer.
     * 
     * @parameter default-value="src/main/resources"
     */
    private File dir;

    /**
     * @parameter
     */
    private String locale;

    /**
     * @parameter default-value="target/classes"
     */
    private String output;

    /** 
     * @parameter expression="${project}" 
     */
    private org.apache.maven.project.MavenProject mavenProject;


    public void execute() throws MojoExecutionException {

        /**
         * arguments to jhindexer
         */
        ArrayList<String> args = new ArrayList<String>();

	if ( locale != null ) {
            args.add( "-locale" );
            args.add( locale );
        }

	if ( output != null ) {
            args.add( "-db" );
            args.add( output );
        }

        args.add( dir.getAbsolutePath() );
        try {
            indexer.compile( args.toArray( new String[args.size()] ) );
        }
        catch ( Exception e ) {
            throw new MojoExecutionException("Java Help indexing exception, a full search database may not have been created.", e );
        }
    }
}
