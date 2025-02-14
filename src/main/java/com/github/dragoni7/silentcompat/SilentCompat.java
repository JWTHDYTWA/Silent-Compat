package com.github.dragoni7.silentcompat;

import com.github.dragoni7.silentcompat.core.registry.MaterialRegistry;
import com.github.dragoni7.silentcompat.core.registry.SilentCompatEffects;
import com.github.dragoni7.silentcompat.core.registry.SilentCompatEntities;
import com.github.dragoni7.silentcompat.core.registry.SilentCompatParticles;
import com.github.dragoni7.silentcompat.core.registry.SilentCompatSoundEvents;
import com.github.dragoni7.silentcompat.event.ClientEventHandler;
import com.github.dragoni7.silentcompat.networking.Networking;
import com.github.dragoni7.silentcompat.trait.AbyssalSynergyTrait;
import com.github.dragoni7.silentcompat.trait.AmplifyingTrait;
import com.github.dragoni7.silentcompat.trait.BlindingTrait;
import com.github.dragoni7.silentcompat.trait.ColdTrait;
import com.github.dragoni7.silentcompat.trait.DecayTrait;
import com.github.dragoni7.silentcompat.trait.DevouringTrait;
import com.github.dragoni7.silentcompat.trait.DimensionalEffectTrait;
import com.github.dragoni7.silentcompat.trait.DiurnalTrait;
import com.github.dragoni7.silentcompat.trait.DodgingTrait;
import com.github.dragoni7.silentcompat.trait.ChocolateChipping;
import com.github.dragoni7.silentcompat.trait.EmuDodgeTrait;
import com.github.dragoni7.silentcompat.trait.EnderFluTrait;
import com.github.dragoni7.silentcompat.trait.FeatherWeightTrait;
import com.github.dragoni7.silentcompat.trait.FireReactTrait;
import com.github.dragoni7.silentcompat.trait.FleetFootedTrait;
import com.github.dragoni7.silentcompat.trait.FreezingTrait;
import com.github.dragoni7.silentcompat.trait.GooeyTrait;
import com.github.dragoni7.silentcompat.trait.HungeringStrike;
import com.github.dragoni7.silentcompat.trait.InverseDimensionalEffectTrait;
import com.github.dragoni7.silentcompat.trait.KnightlyTrait;
import com.github.dragoni7.silentcompat.trait.KnockoutTrait;
import com.github.dragoni7.silentcompat.trait.LavaVisionTrait;
import com.github.dragoni7.silentcompat.trait.LuckyBreak;
import com.github.dragoni7.silentcompat.trait.NeptunesMightTrait;
import com.github.dragoni7.silentcompat.trait.NocturnalTrait;
import com.github.dragoni7.silentcompat.trait.PurifyingTrait;
import com.github.dragoni7.silentcompat.trait.RestorationTrait;
import com.github.dragoni7.silentcompat.trait.RetaliatingBlastTrait;
import com.github.dragoni7.silentcompat.trait.ScorchingTrait;
import com.github.dragoni7.silentcompat.trait.JoltHitTrait;
import com.github.dragoni7.silentcompat.trait.SoulStealTrait;
import com.github.dragoni7.silentcompat.trait.SunSpotTrait;
import com.github.dragoni7.silentcompat.trait.UmbralBlastTrait;
import com.github.dragoni7.silentcompat.trait.UnstableMagicTrait;
import com.github.dragoni7.silentcompat.trait.VigorousTrait;
import com.github.dragoni7.silentcompat.trait.VolitileTrait;
import com.github.dragoni7.silentcompat.trait.VorpalTrait;
import com.mojang.logging.LogUtils;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.silentchaos512.gear.api.GearApi;
import net.silentchaos512.gear.api.traits.ITraitSerializer;

import org.slf4j.Logger;

@Mod(SilentCompat.MODID)
public class SilentCompat {
	public static final String MODID = "silentcompat";
	public static final Logger LOGGER = LogUtils.getLogger();

	public SilentCompat() {
		
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		SilentCompatSoundEvents.SOUND_EVENTS.register(modEventBus);
		SilentCompatParticles.PARTICLES.register(modEventBus);
		SilentCompatEffects.MOB_EFFECTS.register(modEventBus);
		SilentCompatEntities.ENTITY_TYPES.register(modEventBus);
		MaterialRegistry.register(modEventBus);
		registerTraits();
		MinecraftForge.EVENT_BUS.register(this);
		modEventBus.addListener(this::commonSetup);

		if (FMLEnvironment.dist == Dist.CLIENT) {
			ClientEventHandler.setUp(modEventBus);
		}
	}

	public void registerTraits() {
		registerTrait(DiurnalTrait.SERIALIZER);
		registerTrait(NocturnalTrait.SERIALIZER);
		registerTrait(DecayTrait.SERIALIZER);
		registerTrait(EnderFluTrait.SERIALIZER);
		registerTrait(DodgingTrait.SERIALIZER);
		registerTrait(ChocolateChipping.SERIALIZER);
		registerTrait(FleetFootedTrait.SERIALIZER);
		registerTrait(FreezingTrait.SERIALIZER);
		registerTrait(FeatherWeightTrait.SERIALIZER);
		registerTrait(VorpalTrait.SERIALIZER);
		registerTrait(HungeringStrike.SERIALIZER);
		registerTrait(SoulStealTrait.SERIALIZER);
		registerTrait(LuckyBreak.SERIALIZER);
		registerTrait(RetaliatingBlastTrait.SERIALIZER);
		registerTrait(KnockoutTrait.SERIALIZER);
		registerTrait(AbyssalSynergyTrait.SERIALIZER);
		registerTrait(VigorousTrait.SERIALIZER);
		registerTrait(GooeyTrait.SERIALIZER);
		registerTrait(UnstableMagicTrait.SERIALIZER);
		registerTrait(KnightlyTrait.SERIALIZER);
		registerTrait(FireReactTrait.SERIALIZER);
		registerTrait(NeptunesMightTrait.SERIALIZER);
		registerTrait(ColdTrait.SERIALIZER);
		registerTrait(EmuDodgeTrait.SERIALIZER);
		registerTrait(JoltHitTrait.SERIALIZER);
		registerTrait(PurifyingTrait.SERIALIZER);
		registerTrait(AmplifyingTrait.SERIALIZER);
		registerTrait(BlindingTrait.SERIALIZER);
		registerTrait(DevouringTrait.SERIALIZER);
		registerTrait(UmbralBlastTrait.SERIALIZER);
		registerTrait(VolitileTrait.SERIALIZER);
		registerTrait(RestorationTrait.SERIALIZER);
		registerTrait(ScorchingTrait.SERIALIZER);
		registerTrait(SunSpotTrait.SERIALIZER);
		registerTrait(LavaVisionTrait.SERIALIZER);
		registerTrait(DimensionalEffectTrait.SERIALIZER);
		registerTrait(InverseDimensionalEffectTrait.SERIALIZER);
	}

	private void registerTrait(ITraitSerializer<?> serializer) {
		GearApi.registerTraitSerializer(serializer);
	}

	private void commonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			Networking.registerMessages();
		});
	}

	public static final CreativeModeTab SilentCompatTab = (new CreativeModeTab("silentcompat") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(MaterialRegistry.MATERIAL_SETS.get("capsid_alloy").ingot.get());
		}
	});
}
