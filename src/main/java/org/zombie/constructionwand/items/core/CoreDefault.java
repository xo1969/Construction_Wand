package org.zombie.constructionwand.items.core;

import net.minecraft.resources.ResourceLocation;
import org.zombie.constructionwand.ConstructionWand;
import org.zombie.constructionwand.api.IWandAction;
import org.zombie.constructionwand.api.IWandCore;
import org.zombie.constructionwand.wand.action.ActionConstruction;

public class CoreDefault implements IWandCore
{
    @Override
    public int getColor() {
        return -1;
    }

    @Override
    public IWandAction getWandAction() {
        return new ActionConstruction();
    }

    @Override
    public ResourceLocation getRegistryName() {
        return ConstructionWand.loc("default");
    }
}
