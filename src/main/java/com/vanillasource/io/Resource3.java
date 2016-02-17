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

import com.vanillasource.function.ThrowingFunction3;
import com.vanillasource.function.ThrowingFunction4;
import com.vanillasource.function.ThrowingConsumer3;

/**
 * A resource container, which throws 3 exceptions.
 */
public interface Resource3<R, E1 extends Exception, E2 extends Exception, E3 extends Exception> {
   /**
    * Use the contained resource and return some value from
    * its processing.
    */
   <V> V use(ThrowingFunction3<R, V, E1, E2, E3> function) throws E1, E2, E3;

   /**
    * Use the contained resource without returning anything.
    */
   default void use(ThrowingConsumer3<R, E1, E2, E3> consumer) throws E1, E2, E3 {
      use(resource -> {
         consumer.accept(resource);
         return null;
      });
   }

   /**
    * Declare another throws clause for the supplied function.
    */
   <E4 extends Exception> Resource4<R, E1, E2, E3, E4> rethrow(Class<E4> e4Class);

   static <R, E1 extends Exception, E2 extends Exception, E3 extends Exception> Resource3<R, E1, E2, E3> resource(ResourceDefinition3<R, E1, E2, E3> definition) {
      return new Resource3<R, E1, E2, E3>() {
         @Override
         public <V> V use(ThrowingFunction3<R, V, E1, E2, E3> function) throws E1, E2, E3 {
            return definition.use(function::apply);
         }

         @Override
         public <E4 extends Exception> Resource4<R, E1, E2, E3, E4> rethrow(Class<E4> e4Class) {
            return Resource4.<R, E1, E2, E3, E4>resource(definition::use);
         }
      };
   }

   interface ResourceDefinition3<R, E1 extends Exception, E2 extends Exception, E3 extends Exception> {
      <V, E4 extends Exception> V use(ThrowingFunction4<R, V, E1, E2, E3, E4> user) throws E1, E2, E3, E4;
   }
}



