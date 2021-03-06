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

package org.javacc.examples.obfuscator;

import java.io.File;
import java.util.Hashtable;

public class Globals {
  // The mappings from old id's to new id's.
  static Hashtable<String, String> mappings = new Hashtable<String, String>();
  // A table of targets of all known mappings.
  static Hashtable<String, String> mapTargets = new Hashtable<String, String>();
  // These id's may not be changed.
  static Hashtable<String, String> noChangeIds = new Hashtable<String, String>();
  // These id's should be used for mappings.
  static Hashtable<String, String> useIds = new Hashtable<String, String>();
  // The location of the input and output directories.
  static File inpDir, outDir;
  // Set to true by Java parser if class has a main program.
  static boolean mainExists;
  // A counter used to generate new identifiers
  static int counter = 0;

  // Returns the map of old to obfuscated id.  If map does not
  // exist, it is created.
  static String map(String str) {
    String obj = mappings.get(str);
    if (obj != null) {
      return obj;
    }
    if (useIds.isEmpty()) {
      String newId = "O0" + counter++;
      mappings.put(str, newId);
      return newId;
    }
    else {
      obj = useIds.keys().nextElement();
      useIds.remove(obj);
      String newId = obj;
      mappings.put(str, newId);
      return newId;
    }
  }
}
