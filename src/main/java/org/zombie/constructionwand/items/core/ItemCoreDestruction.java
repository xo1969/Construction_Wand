package org.zombie.constructionwand.items.core;

import net.minecraft.resources.ResourceLocation;
import org.zombie.constructionwand.ConstructionWand;
import org.zombie.constructionwand.api.IWandAction;
import org.zombie.constructionwand.wand.action.ActionDestruction;

public class ItemCoreDestruction extends ItemCore
{
    public ItemCoreDestruction(Properties properties) {
        super(properties);
    }

    @Override
    public int getColor() {
        return 0xFF0000;
    }

    @Override
    public IWandAction getWandAction() {
        return new ActionDestruction();
    }

    @Override
    public ResourceLocation getRegistryName() {
        return ConstructionWand.loc("core_destruction");
    }
}
