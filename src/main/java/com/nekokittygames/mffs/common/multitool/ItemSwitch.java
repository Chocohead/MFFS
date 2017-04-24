/*  
    Copyright (C) 2012 Thunderdark

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    
    Contributors:
    Thunderdark - initial implementation
 */

package com.nekokittygames.mffs.common.multitool;

import com.nekokittygames.mffs.libs.LibItemNames;
import com.nekokittygames.mffs.libs.LibMisc;
import com.nekokittygames.mffs.api.ISwitchabel;
import com.nekokittygames.mffs.common.Functions;
import com.nekokittygames.mffs.common.SecurityHelper;
import com.nekokittygames.mffs.common.SecurityRight;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemSwitch extends ItemMultitool {

	public ItemSwitch() {
		super(1);
		setUnlocalizedName(LibMisc.UNLOCALIZED_PREFIX+ LibItemNames.MULTITOOL_SWITCH);
		setRegistryName(LibItemNames.MULTITOOL_SWITCH);

	}

	@Override
	public EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
		if (world.isRemote)
			return EnumActionResult.PASS;

		TileEntity tileentity = world.getTileEntity(pos);

		if (tileentity instanceof ISwitchabel) {

			if (SecurityHelper.isAccessGranted(tileentity, player, world,
					SecurityRight.EB)) {

				if (((ISwitchabel) tileentity).isSwitchabel()) {
					if (this.consumePower(stack, 1000, true)) {
						this.consumePower(stack, 1000, false);
						((ISwitchabel) tileentity).toggelSwitchValue();
						return EnumActionResult.SUCCESS;
					} else {

						Functions.ChattoPlayer(player, "multitool.notEnoughFE");
						return EnumActionResult.FAIL;
					}
				} else {

					Functions.ChattoPlayer(player, "multitool.notInSwitchMode");
					return EnumActionResult.FAIL;
				}

			}

		}

		return EnumActionResult.PASS;
	}



}
