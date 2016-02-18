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

package com.vanillasource.function;

import org.testng.annotations.*;
import static org.testng.Assert.*;
import java.io.IOException;
import static com.vanillasource.function.Blocks.*;

@Test
public class BlockTests {
   public void testBlockCanBeUsedDirectlyWithoutExceptions() {
      take(() -> "Return");
   }

   public void testVoidBlockCanBeUsedDirectlyWithoutExceptions() {
      take(() -> {});
   }

   public void testBlockReturnsValue() {
      assertEquals(take(() -> "Return"), "Return");
   }

   @Test(expectedExceptions = ClassNotFoundException.class)
   public void testDeclaredExceptionMustBeDeclaredAndIsRethrown() throws ClassNotFoundException {
      take(() -> {
         if (1==1) {
            throw new ClassNotFoundException("test");
         }
      });
   }

   public void testPureLambdaCanFallBackToException() throws Exception {
      take(() -> {
         if (1==2) {
            throw new ClassNotFoundException("test");
         }
         if (1==3) {
            throw new IOException("test");
         }
         return "Return";
      });
   }

   public void testRethrowsLocalizesSingleException() throws ClassNotFoundException {
      take(rethrow(ClassNotFoundException.class).block( () -> {
         if (1==2) {
            throw new ClassNotFoundException("test");
         }
      }));
   }

   public void testRethrowsLocalizesTwoExceptions() throws ClassNotFoundException, IOException {
      take(rethrow(ClassNotFoundException.class)
          .rethrow(IOException.class)
          .block( () -> {
             if (1==2) {
                throw new ClassNotFoundException("test");
             }
             if (1==3) {
                throw new IOException("test");
             }
          }));
   }

   public void testBlockReturnsReturnValue() {
      assertEquals(take(block(() -> "Value")), "Value");
   }

   public void testBlockCanTakeVoidType() {
      take(block(() -> {
      }));
   }

   public void testBlockCanBeMadeOutOfMethodHandleWithoutException() {
      take(this::makeStringValueWithoutException);
   }

   public void testBlockCanBeMadeOutOfMethodHandleWithException() throws ClassNotFoundException {
      take(this::makeStringValueWithException);
   }

   @Test(expectedExceptions = ClassNotFoundException.class)
   public void testVoidBlockWithTwoParametersAndAnException() throws ClassNotFoundException {
      take((s, i) -> {
         throw new ClassNotFoundException("test");
      });
   }

   public void testVoidBlockWithTwoParametersAndTwoExceptions() throws ClassNotFoundException, IOException {
      take(rethrow(ClassNotFoundException.class).rethrow(IOException.class).block((s, i) -> {
         if (1==2) {
            throw new ClassNotFoundException("test");
         }
      }));
   }

   private String makeStringValueWithoutException() {
      return "Value";
   }

   private String makeStringValueWithException() throws ClassNotFoundException {
      return "Value";
   }

   private <R, E1 extends Exception, E2 extends Exception, E3 extends Exception, E4 extends Exception, E5 extends Exception>
         R take(Block<R, E1, E2, E3, E4, E5> block) throws E1, E2, E3, E4, E5 {
      return block.run();
   }

   private <E1 extends Exception, E2 extends Exception, E3 extends Exception, E4 extends Exception, E5 extends Exception>
         void take(VoidBlock<E1, E2, E3, E4, E5> block) throws E1, E2, E3, E4, E5 {
      block.run();
   }

   private <E1 extends Exception, E2 extends Exception, E3 extends Exception, E4 extends Exception, E5 extends Exception>
         void take(VoidBlock2<String, Integer, E1, E2, E3, E4, E5> block) throws E1, E2, E3, E4, E5 {
      block.run("Param1", 2);
   }
}

