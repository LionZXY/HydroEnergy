package com.sinthoras.hydroenergy.blocks;

import com.github.technus.tectech.thing.CustomItemList;
import com.sinthoras.hydroenergy.HE;
import com.sinthoras.hydroenergy.config.HEConfig;
import gregtech.api.GregTech_API;
import gregtech.api.enums.*;
import gregtech.api.interfaces.IItemContainer;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class HEBlockRecipes {

    // TODO: Fill up recipe components as needed
    public static void registerRecipes() {
        // ULV is disabled!
        IItemContainer[] hulls = {
                null,                       // ULV,
                ItemList.Hull_LV,
                ItemList.Hull_MV,
                ItemList.Hull_HV,
                ItemList.Hull_EV,
                ItemList.Hull_IV,
                ItemList.Hull_LuV,
                ItemList.Hull_ZPM,
                ItemList.Hull_UV,
                ItemList.Hull_MAX,          // UHV
                CustomItemList.Hull_UEV,
                CustomItemList.Hull_UIV,
                CustomItemList.Hull_UMV,
                CustomItemList.Hull_UXV,
                CustomItemList.Hull_OPV,
                CustomItemList.Hull_MAXV
        };
        IItemContainer[] motors = {
                null,                       // ULV,
                ItemList.Electric_Motor_LV,
                ItemList.Electric_Motor_MV,
                ItemList.Electric_Motor_HV,
                ItemList.Electric_Motor_EV,
                ItemList.Electric_Motor_IV,
                ItemList.Electric_Motor_LuV,
                ItemList.Electric_Motor_ZPM,
                ItemList.Electric_Motor_UV,
                ItemList.Electric_Motor_UHV,
                ItemList.Electric_Motor_UEV,
                null,                       // UIV
                null,                       // UMV
                null,                       // UXV
                null,                       // OpV
                null                        // MAX
        };
        IItemContainer[] pumps = {
                null,                       // ULV,
                ItemList.Electric_Pump_LV,
                ItemList.Electric_Pump_MV,
                ItemList.Electric_Pump_HV,
                ItemList.Electric_Pump_EV,
                ItemList.Electric_Pump_IV,
                ItemList.Electric_Pump_LuV,
                ItemList.Electric_Pump_ZPM,
                ItemList.Electric_Pump_UV,
                ItemList.Electric_Pump_UHV,
                ItemList.Electric_Pump_UEV,
                null,                       // UIV
                null,                       // UMV
                null,                       // UXV
                null,                       // OpV
                null                        // MAX
        };
        Materials[] rotorMaterialsPerVoltage = {
                null,                       // ULV,
                Materials.Steel,            // LV
                Materials.Aluminium,        // MV
                Materials.StainlessSteel,   // HV
                Materials.Titanium,         // EV
                Materials.TungstenSteel,    // IV
                Materials.Palladium,        // LuV
                Materials.Iridium,          // ZPM
                Materials.Osmium,           // UV
                Materials.Neutronium,       // UHV
                Materials.Bedrockium,       // UEV
                Materials.BlackPlutonium,   // UIV
                Materials.Draconium,        // UMV
                null,                       // UXV
                null,                       // OpV
                null                        // MAX
        };
        Materials[] cableMaterialsPerVoltage = {
                null,                       // ULV,
                Materials.Tin,              // LV
                Materials.Copper,           // MV
                Materials.Gold,             // HV
                Materials.Aluminium,        // EV
                Materials.Platinum,         // IV
                Materials.VanadiumGallium,  // LuV
                Materials.Naquadah,         // ZPM
                Materials.NaquadahAlloy,    // UV
                Materials.SuperconductorUV, // UHV
                Materials.Draconium,        // UEV
                Materials.NetherStar,       // UIV
                Materials.Quantium,         // UMV
                null,                       // UXV
                null,                       // OpV
                null                        // MAX
        };

        for (Materials material : Materials.values()) {
            if (material.mStandardMoltenFluid != null && material.contains(SubTag.SOLDERING_MATERIAL)
                    && !(GregTech_API.mUseOnlyGoodSolderingMaterials
                    && !material.contains(SubTag.SOLDERING_MATERIAL_GOOD))) {
                int multiplier = material.contains(SubTag.SOLDERING_MATERIAL_GOOD) ? 1 : material.contains(SubTag.SOLDERING_MATERIAL_BAD) ? 4 : 2;


                GT_Values.RA.addAssemblerRecipe(new ItemStack[] {
                                ItemList.Casing_SolidSteel.get(1L),
                                new ItemStack(GregTech_API.sBlockConcretes, 2, 8),
                                ItemList.Cover_Screen.get(1L),
                                ItemList.FluidRegulator_LV.get(2L),
                                GT_Utility.getIntegratedCircuit(1)
                        },
                        material.getMolten(144L * multiplier / 2L),
                        HE.hydroDamControllerBlock,
                        200,
                        30);

                for(int tierId=0;tierId<HE.hydroPumpBlocks.length;tierId++) {
                    if(HEConfig.enabledTiers[tierId]) {
                        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{
                                        hulls[tierId].get(1L),
                                        GT_OreDictUnificator.get(OrePrefixes.rotor, rotorMaterialsPerVoltage[tierId], 2L),
                                        motors[tierId].get(1L),
                                        pumps[tierId].get(1L),
                                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, cableMaterialsPerVoltage[tierId], 2L),
                                        GT_Utility.getIntegratedCircuit(1)
                                },
                                material.getMolten(144L * multiplier / 2L),
                                HE.hydroPumpBlocks[tierId],
                                200,
                                30);

                        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{
                                        hulls[tierId].get(1L),
                                        GT_OreDictUnificator.get(OrePrefixes.rotor, rotorMaterialsPerVoltage[tierId], 2L),
                                        motors[tierId].get(1L),
                                        pumps[tierId].get(1L),
                                        GT_OreDictUnificator.get(OrePrefixes.cableGt01, cableMaterialsPerVoltage[tierId], 2L),
                                        GT_Utility.getIntegratedCircuit(2)
                                },
                                material.getMolten(144L * multiplier / 2L),
                                HE.hydroTurbineBlocks[tierId],
                                200,
                                30);
                    }
                }
            }
        }
    }
}

