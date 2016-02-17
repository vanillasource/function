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

import com.vanillasource.function.VoidException;
import com.vanillasource.function.ThrowingFunction1;
import com.vanillasource.function.ThrowingFunction4;
import com.vanillasource.function.ThrowingConsumer1;

/**
 * A resource container, which protects the actual resource
 * contained "inside" it. Instead of leaking the resource out of
 * this container, appropriate code needs to be submitted to
 * work on the resource.
 */
public interface Resource<R> {
   /**
    * Use the contained resource and return some value from
    * its processing.
    */
   <V, E extends Exception> V use(ThrowingFunction1<R, V, E> function) throws E;

   /**
    * Use the contained resource without returning anything.
    */
   default <E extends Exception> void use(ThrowingConsumer1<R, E> consumer) throws E {
      use(resource -> {
         consumer.accept(resource);
         return null;
      });
   }

   /**
    * Declare a throws clause for the supplied function.
    */
   <E1 extends Exception> Resource1<R, E1> rethrow(Class<E1> e1Class);

   static <R> Resource<R> resource(ResourceDefinition<R> definition) {
      return new Resource<R>() {
         @Override
         public <V, E extends Exception> V use(ThrowingFunction1<R, V, E> function) throws E {
            return definition.use(function::apply);
         }

         @Override
         public <E1 extends Exception> Resource1<R, E1> rethrow(Class<E1> e1Class) {
            return Resource1.<R, E1>resource(definition::use);
         }
      };
   }

   interface ResourceDefinition<R> {
      <V, E1 extends Exception, E2 extends Exception, E3 extends Exception, E4 extends Exception> V use(ThrowingFunction4<R, V, E1, E2, E3, E4> user) throws E1, E2, E3, E4;
   }
}

