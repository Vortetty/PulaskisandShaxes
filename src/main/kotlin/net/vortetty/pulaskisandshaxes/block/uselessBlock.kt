package net.vortetty.pulaskisandshaxes.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.ShapeContext
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView


open class UselessBlock(transparent: Boolean, voxelShape: VoxelShape?=null, settings: Settings?) : Block(settings) {
   private val transp: Boolean
   private val voxelShape: VoxelShape?

   override fun isSideInvisible(state: BlockState, stateFrom: BlockState, direction: Direction): Boolean {
      return if (transp) {
         if (stateFrom.isOf(this)) {
              true
         } else {
              super.isSideInvisible(state, stateFrom, direction)
         }
      } else {
         false
      }
   }

   override fun getOutlineShape(state: BlockState?, view: BlockView?, pos: BlockPos?, context: ShapeContext?): VoxelShape? {
      if(voxelShape == null){
         return VoxelShapes.fullCube()
      }
      return voxelShape
   }

   init {
      this.transp = transparent
      this.voxelShape = voxelShape
   }
}