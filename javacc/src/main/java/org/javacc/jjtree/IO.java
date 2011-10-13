/* Copyright (c) 2006, Sun Microsystems, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Sun Microsystems, Inc. nor the names of its
 *       contributors may be used to endorse or promote products derived from
 *       this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.javacc.jjtree;

import org.javacc.utils.Tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;

final class IO {
  private String ifn;
  private String ofn;
  private Reader in;
  private PrintWriter out;
  private PrintStream msg;
  private PrintStream err;

  IO() {
    ifn = "<uninitialized input>";
    msg = System.out;
    err = System.err;
  }

  String getInputFileName() {
    return ifn;
  }

  Reader getIn() {
    return in;
  }

  String getOutputFileName() {
    return ofn;
  }

  PrintWriter getOut() {
    return out;
  }

  PrintStream getMsg() {
    return msg;
  }

  PrintStream getErr() {
    return err;
  }

  void print(String s) {
    out.print(s);
  }

  void println(String s) {
    out.println(s);
  }

  void println() {
    out.println();
  }

  void closeAll() {
    if (out != null) { out.close(); }
    if (msg != null) { msg.flush(); }
    if (err != null) { err.flush(); }
  }

  private String createOutputFileName(String i) {
    String o = JJTreeOptions.getOutputFile();

    if (o.equals("")) {
      int s = i.lastIndexOf(File.separatorChar);
      if (s >= 0) {
        i = i.substring(s + 1);
      }

      int di = i.lastIndexOf('.');
      if (di == -1) {
        o = i + ".jj";
      }
      else {
        String suffix = i.substring(di);
        if (suffix.equals(".jj")) {
          o = i + ".jj";
        }
        else {
          o = i.substring(0, di) + ".jj";
        }
      }
    }

    return o;
  }

  void setInput(String fn) throws IOException {
    try {
      File fp = new File(fn);
      if (!fp.exists()) {
        throw new IOException("File " + fn + " not found.");
      }
      if (fp.isDirectory()) {
        throw new IOException(fn + " is a directory. Please use a valid file name.");
      }
      ifn = fp.getPath();
      in = new BufferedReader(new InputStreamReader(new FileInputStream(ifn), JJTreeOptions.getGrammarEncoding()));
    }
    catch (SecurityException se) {
      throw new IOException("Security violation while trying to open " + fn);
    }
    catch (FileNotFoundException e) {
      throw new IOException("File " + fn + " not found.");
    }
    catch (IOException ioe) {
      throw new IOException(ioe.toString());
    }
  }

  void setOutput() throws IOException {
    try {
      Tools.createOutputDir(JJTreeOptions.getJJTreeOutputDirectory());
      File file = new File(JJTreeOptions.getJJTreeOutputDirectory(), createOutputFileName(ifn));
      ofn = file.toString();
      out = new PrintWriter(new FileWriter(file));
    }
    catch (IOException ioe) {
      throw new IOException("Can't create output file " + ofn);
    }
  }
}
