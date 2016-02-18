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
import com.vanillasource.function.VoidBlock1;

/**
 * A protected resource that can only be accessed by supplying
 * a block of code to work with the resource. This is essentially a "safer"
 * try-with-resources block, that works with any objects, not just closeable
 * ones.
 */
public interface Resource<R, RE1 extends Exception, RE2 extends Exception, RE3 extends Exception, RE4 extends Exception, RE5 extends Exception> {
   <V, E1 extends Exception, E2 extends Exception, E3 extends Exception, E4 extends Exception, E5 extends Exception>
         V use(Block1<R, V, E1, E2, E3, E4, E5> block) throws E1, E2, E3, E4, E5, RE1, RE2, RE3, RE4, RE5;

   default <E1 extends Exception, E2 extends Exception, E3 extends Exception, E4 extends Exception, E5 extends Exception>
         void use(VoidBlock1<R, E1, E2, E3, E4, E5> voidBlock) throws E1, E2, E3, E4, E5, RE1, RE2, RE3, RE4, RE5 {
      use(voidBlock.toBlock1());
   }
}

