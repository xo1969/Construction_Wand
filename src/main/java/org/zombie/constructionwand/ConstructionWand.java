package org.zombie.constructionwand;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.zombie.constructionwand.basics.ConfigClient;
import org.zombie.constructionwand.basics.ConfigServer;
import org.zombie.constructionwand.basics.ModStats;
import org.zombie.constructionwand.client.ClientEvents;
import org.zombie.constructionwand.client.RenderBlockPreview;
import org.zombie.constructionwand.component.ModDataComponents;
import org.zombie.constructionwand.containers.ContainerManager;
import org.zombie.constructionwand.containers.ContainerRegistrar;
import org.zombie.constructionwand.crafting.ModRecipes;
import org.zombie.constructionwand.items.ModItems;
import org.zombie.constructionwand.network.ModMessages;
import org.zombie.constructionwand.wand.undo.UndoHistory;


@Mod(ConstructionWand.MODID)
public class ConstructionWand {
    public static final String MODID = "constructionwand";
    public static final String MODNAME = "ConstructionWand";

    public static ConstructionWand instance;
    public static final Logger LOGGER = LogManager.getLogger();

    public ContainerManager containerManager;
    public UndoHistory undoHistory;
    public RenderBlockPreview renderBlockPreview;

    public ConstructionWand(IEventBus eventBus, ModContainer container, Dist dist) {
        instance = this;

        containerManager = new ContainerManager();
        undoHistory = new UndoHistory();

        // Register setup methods for modloading
        eventBus.addListener(this::commonSetup);
        eventBus.addListener(this::clientSetup);
        // Register packets
        eventBus.addListener(ModMessages::registerPayloads);

        // Register Item DeferredRegister
        ModDataComponents.DATA_COMPONENT_TYPES.register(eventBus);
        ModItems.ITEMS.register(eventBus);
        ModRecipes.RECIPE_SERIALIZERS.register(eventBus);
        ModStats.CUSTOM_STATS.register(eventBus);

        // Config setup
        container.registerConfig(ModConfig.Type.SERVER, ConfigServer.SPEC);
        container.registerConfig(ModConfig.Type.CLIENT, ConfigClient.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("ConstructionWand says hello - may the odds be ever in your favor.");

        // Container registry
        ContainerRegistrar.register();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        renderBlockPreview = new RenderBlockPreview();
        NeoForge.EVENT_BUS.register(renderBlockPreview);
        NeoForge.EVENT_BUS.register(new ClientEvents());

        event.enqueueWork(ModItems::registerModelProperties);
    }

    public static ResourceLocation loc(String name) {
        return ResourceLocation.fromNamespaceAndPath(MODID, name);
    }
}
