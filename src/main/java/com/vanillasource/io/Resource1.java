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

import com.vanillasource.function.ThrowingFunction1;
import com.vanillasource.function.ThrowingFunction4;
import com.vanillasource.function.ThrowingConsumer1;

/**
 * A resource container, which throws 1 exception.
 */
public interface Resource1<R, E1 extends Exception> {
   /**
    * Use the contained resource and return some value from
    * its processing.
    */
   <V> V use(ThrowingFunction1<R, V, E1> function) throws E1;

   /**
    * Use the contained resource without returning anything.
    */
   default void use(ThrowingConsumer1<R, E1> consumer) throws E1 {
      use(resource -> {
         consumer.accept(resource);
         return null;
      });
   }

   /**
    * Declare another throws clause for the supplied function.
    */
   <E2 extends Exception> Resource2<R, E1, E2> rethrow(Class<E2> e2Class);

   static <R, E1 extends Exception> Resource1<R, E1> resource(ResourceDefinition1<R, E1> definition) {
      return new Resource1<R, E1>() {
         @Override
         public <V> V use(ThrowingFunction1<R, V, E1> function) throws E1 {
            return definition.use(function::apply);
         }

         @Override
         public <E2 extends Exception> Resource2<R, E1, E2> rethrow(Class<E2> e2Class) {
            return Resource2.<R, E1, E2>resource(definition::use);
         }
      };
   }

   interface ResourceDefinition1<R, E1 extends Exception> {
      <V, E2 extends Exception, E3 extends Exception, E4 extends Exception> V use(ThrowingFunction4<R, V, E1, E2, E3, E4> user) throws E1, E2, E3, E4;
   }
}

