//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package awesoft.hammers.items.itemtools;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Map;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.item.tool.ItemTool;
import net.minecraft.core.item.tool.ItemToolPickaxe;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

public class ItemToolHammer extends ItemTool {
	public static Map<Block, Integer> miningLevels = ItemToolPickaxe.miningLevels;

	public ItemToolHammer(String name, int id, ToolMaterial enumtoolmaterial) {
		super(name, id, 2, enumtoolmaterial, BlockTags.MINEABLE_BY_PICKAXE);
	}

	public boolean canHarvestBlock(Block block) {
		Integer miningLevel = (Integer) miningLevels.get(block);
		if (miningLevel != null) {
			return this.material.getMiningLevel() >= miningLevel;
		} else {
			return block.hasTag(BlockTags.MINEABLE_BY_PICKAXE);
		}
	}

/*
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
		float xRot = entityplayer.xRot % 360;
		float yRot = Math.abs(entityplayer.yRot % 360);
		DecimalFormat df = new DecimalFormat("0.##");
		df.setRoundingMode(RoundingMode.DOWN);
		entityplayer.addChatMessage("Y Rot:"+df.format(yRot)+" X Rot:"+df.format(xRot));
		return true;
	}
*/

	@Override
	public boolean onBlockDestroyed(World world, ItemStack itemstack, int i, int j, int k, int l, EntityLiving entityliving) {
		super.onBlockDestroyed(world, itemstack, i, j, k, l, entityliving);
		int x = j;
		int y = k;
		int z = l;

		float xRot = entityliving.xRot % 360;
		float yRot = Math.abs(entityliving.yRot % 360);

		// Up/Down
		if (xRot < -40.0 || xRot > 40.0)
		{
			MineBlock(x,y,z+1,entityliving.world);
			MineBlock(x,y,z-1,entityliving.world);

			MineBlock(x+1,y,z+1,entityliving.world);
			MineBlock(x+1,y,z,entityliving.world);
			MineBlock(x+1,y,z-1,entityliving.world);

			MineBlock(x-1,y,z+1,entityliving.world);
			MineBlock(x-1,y,z,entityliving.world);
			MineBlock(x-1,y,z-1,entityliving.world);
		}

		// North/South
		else if ((yRot >= 315 || (yRot >= 0 && yRot<= 45)) || yRot <= 225 && yRot >= 135)
		{
			MineBlock(x,y+1,z,entityliving.world);
			MineBlock(x,y-1,z,entityliving.world);

			MineBlock(x-1,y+1,z,entityliving.world);
			MineBlock(x-1,y,z,entityliving.world);
			MineBlock(x-1,y-1,z,entityliving.world);

			MineBlock(x+1,y+1,z,entityliving.world);
			MineBlock(x+1,y,z,entityliving.world);
			MineBlock(x+1,y-1,z,entityliving.world);
		}

		// East/West
		else if ((yRot >= 45 && yRot <= 135) || (yRot >= 225 && yRot <= 315))
		{
			MineBlock(x,y+1,z,entityliving.world);
			MineBlock(x,y-1,z,entityliving.world);

			MineBlock(x,y+1,z+1,entityliving.world);
			MineBlock(x,y,z+1,entityliving.world);
			MineBlock(x,y-1,z+1,entityliving.world);

			MineBlock(x,y+1,z-1,entityliving.world);
			MineBlock(x,y,z-1,entityliving.world);
			MineBlock(x,y-1,z-1,entityliving.world);
		}

		return true;
	}

	protected void MineBlock(int x, int y, int z, World world) {
		if (world.getBlock(x, y, z) != null) {
			if (world.getBlock(x,y,z).id != Block.bedrock.id) {
				ItemStack[] item = world.getBlock(x, y, z).getBreakResult(world, EnumDropCause.PROPER_TOOL, x, y, z, world.getBlockMetadata(x, y, z), world.getBlockTileEntity(x, y, z));
				world.setBlockWithNotify(x, y, z, 0);
				if (item != null) {
					for (ItemStack itemStack : item) {
						if (itemStack != null) {
							world.dropItem(x, y, z, itemStack);
						}
					}
				}
			}
		}
	}
}
