/**
 * Copyright (C) 2016 VanillaSource
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.vanillasource.io;

import com.vanillasource.function.Block1;
import com.vanillasource.function.Block;
import java.util.function.Supplier;

/**
 * Utility class to create resources.
 */
public class Resources {
   private Resources() {
   }

   /**
    * Create a resource from an <code>AutoCloseable</code> resource, and
    * embeds it into a try-with-resources block to be closed.
    */
   public static <R extends AutoCloseable>
         Resource<R, Exception, Exception, Exception, Exception, Exception> closeableResource(Block<R, Exception, Exception, Exception, Exception, Exception> resourceBlock) {
      return new Resource<R, Exception, Exception, Exception, Exception, Exception>() {
         @Override
         public <V, E1 extends Exception, E2 extends Exception, E3 extends Exception, E4 extends Exception, E5 extends Exception>
               V use(Block1<R, V, E1, E2, E3, E4, E5> block) throws Exception {
            try (R resource = resourceBlock.run()) {
               return block.run(resourceBlock.run());
            }
         }
      };
   }
}

