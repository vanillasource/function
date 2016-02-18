/**
 * Copyright (C) 2015 VanillaSource
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

/**
 * Utility class to create different types of code blocks with different
 * exceptions.
 */
public class Blocks {
   private Blocks() {
   }

   /**
    * Identity factory for convenience.
    */
   public static <R> Block<R, VoidException, VoidException, VoidException, VoidException, VoidException> block(Block<R, VoidException, VoidException, VoidException, VoidException, VoidException> block) {
      return block;
   }

   /**
    * Read a void block and convert it to a proper block that returns (Void) null.
    */
   public static Block<Void, VoidException, VoidException, VoidException, VoidException, VoidException> block(VoidBlock<VoidException, VoidException, VoidException, VoidException, VoidException> voidBlock) {
      return () -> {
         voidBlock.run();
         return null;
      };
   }

   /**
    * Start a block builder that fixes the first exception to some given type.
    */
   public static <E1 extends Exception> BlockBuilderContinue1<E1, VoidException, VoidException, VoidException, VoidException> rethrow(Class<E1> e1Class) {
      return new BlockBuilderContinue1<>();
   }

   public static class BlockBuilder<E1 extends Exception, E2 extends Exception, E3 extends Exception, E4 extends Exception, E5 extends Exception> {
      public <R> Block<R, E1, E2, E3, E4, E5> block(Block<R, E1, E2, E3, E4, E5> block) {
         return block;
      }

      public Block<Void, E1, E2, E3, E4, E5> block(VoidBlock<E1, E2, E3, E4, E5> voidBlock) {
         return () -> {
            voidBlock.run();
            return null;
         };
      }
   }

   public static class BlockBuilderContinue1<E1 extends Exception, E2 extends Exception, E3 extends Exception, E4 extends Exception, E5 extends Exception>
         extends BlockBuilder<E1, E2, E3, E4, E5> {
      public <E2 extends Exception> BlockBuilderContinue2<E1, E2, VoidException, VoidException, VoidException> rethrow(Class<E2> e2Class) {
         return new BlockBuilderContinue2<>();
      }
   }

   public static class BlockBuilderContinue2<E1 extends Exception, E2 extends Exception, E3 extends Exception, E4 extends Exception, E5 extends Exception>
         extends BlockBuilder<E1, E2, E3, E4, E5> {
      public <E2 extends Exception> BlockBuilderContinue3<E1, E2, VoidException, VoidException, VoidException> rethrow(Class<E2> e2Class) {
         return new BlockBuilderContinue3<>();
      }
   }

   public static class BlockBuilderContinue3<E1 extends Exception, E2 extends Exception, E3 extends Exception, E4 extends Exception, E5 extends Exception>
         extends BlockBuilder<E1, E2, E3, E4, E5> {
      public <E3 extends Exception> BlockBuilderContinue4<E1, E2, E3, VoidException, VoidException> rethrow(Class<E3> e3Class) {
         return new BlockBuilderContinue4<>();
      }
   }

   public static class BlockBuilderContinue4<E1 extends Exception, E2 extends Exception, E3 extends Exception, E4 extends Exception, E5 extends Exception>
         extends BlockBuilder<E1, E2, E3, E4, E5> {
      public <E4 extends Exception> BlockBuilderContinue5<E1, E2, E3, E4, VoidException> rethrow(Class<E4> e4Class) {
         return new BlockBuilderContinue5<>();
      }
   }

   public static class BlockBuilderContinue5<E1 extends Exception, E2 extends Exception, E3 extends Exception, E4 extends Exception, E5 extends Exception>
         extends BlockBuilder<E1, E2, E3, E4, E5> {
      public <E5 extends Exception> BlockBuilder<E1, E2, E3, E4, E5> rethrow(Class<E5> e5Class) {
         return new BlockBuilder<>();
      }
   }
}


