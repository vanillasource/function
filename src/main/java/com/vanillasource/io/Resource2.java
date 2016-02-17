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

import com.vanillasource.function.ThrowingFunction2;
import com.vanillasource.function.ThrowingFunction4;
import com.vanillasource.function.ThrowingConsumer2;

/**
 * A resource container, which throws 2 exceptions.
 */
public interface Resource2<R, E1 extends Exception, E2 extends Exception> {
   /**
    * Use the contained resource and return some value from
    * its processing.
    */
   <V> V use(ThrowingFunction2<R, V, E1, E2> function) throws E1, E2;

   /**
    * Use the contained resource without returning anything.
    */
   default void use(ThrowingConsumer2<R, E1, E2> consumer) throws E1, E2 {
      use(resource -> {
         consumer.accept(resource);
         return null;
      });
   }

   /**
    * Declare another throws clause for the supplied function.
    */
   <E3 extends Exception> Resource3<R, E1, E2, E3> rethrow(Class<E3> e3Class);

   static <R, E1 extends Exception, E2 extends Exception> Resource2<R, E1, E2> resource(ResourceDefinition2<R, E1, E2> definition) {
      return new Resource2<R, E1, E2>() {
         @Override
         public <V> V use(ThrowingFunction2<R, V, E1, E2> function) throws E1, E2 {
            return definition.use(function::apply);
         }

         @Override
         public <E3 extends Exception> Resource3<R, E1, E2, E3> rethrow(Class<E3> e3Class) {
            return Resource3.<R, E1, E2, E3>resource(definition::use);
         }
      };
   }

   interface ResourceDefinition2<R, E1 extends Exception, E2 extends Exception> {
      <V, E3 extends Exception, E4 extends Exception> V use(ThrowingFunction4<R, V, E1, E2, E3, E4> user) throws E1, E2, E3, E4;
   }
}


