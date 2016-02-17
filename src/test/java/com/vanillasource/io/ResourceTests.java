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

import org.testng.annotations.*;
import static org.testng.Assert.*;
import com.vanillasource.function.ThrowingFunction4;
import java.io.IOException;

@Test
public class ResourceTests {
   private Resource<String> resource;

   public void testResourceWithoutExceptionDoesNotDefineAnyExceptions() {
      resource.use(r -> "Used: "+r);
   }

   public void testReturnValueIsReturned() {
      assertEquals(resource.use(r -> "Used: "+r), "Used: Resource");
   }

   @Test(expectedExceptions = ClassNotFoundException.class)
   public void testDeclaredExceptionMustBeDeclaredAndIsRethrown() throws ClassNotFoundException {
      resource.use(r -> {
         if (1==1) {
            throw new ClassNotFoundException("test");
         }
      });
   }

   public void testTwoDeclaredExceptionsMustUseRethrows() throws ClassNotFoundException, IOException {
      resource
         .rethrow(ClassNotFoundException.class)
         .rethrow(IOException.class)
         .use(r -> {
            if (1==2) {
               throw new ClassNotFoundException("test");
            }
            if (1==3) {
               throw new IOException("test");
            }
         });
   }

   /*
   public void testMandatoryCallCanBeMethodHandle() {
   }

   public void executeSomeLogic(String resource) {
   }
   */

   @BeforeMethod
   protected void setUp() {
      resource = Resource.<String>resource(new Resource.ResourceDefinition<String>() {
         @Override
         public <V, E1 extends Exception, E2 extends Exception, E3 extends Exception, E4 extends Exception> V use(ThrowingFunction4<String, V, E1, E2, E3, E4> function) throws E1, E2, E3, E4 {
            return function.apply("Resource");
         }
      });

   }
}
