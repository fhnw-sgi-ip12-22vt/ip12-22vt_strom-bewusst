package ch.fhnw.strombewusst.collision;
import ch.fhnw.strombewusst.EntityType;
import ch.fhnw.strombewusst.components.DeskComponent;
import ch.fhnw.strombewusst.components.PlayerComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.physics.CollisionHandler;

import static com.almasb.fxgl.dsl.FXGL.spawn;

public class PlayerDeskHandler extends CollisionHandler {

    public PlayerDeskHandler() {
        super(EntityType.PLAYER, EntityType.DESK);
    }

    Entity message1,message2;
    @Override
    protected void onCollisionBegin(Entity player, Entity desk){
        if(player.getComponent(PlayerComponent.class).getPlayerNum() == 1){
            message1 = spawn("message",new SpawnData(920,20).put("msgNum",desk.getComponent(DeskComponent.class).getDeskNum()));
        }
        else{
            message2 = spawn("message",new SpawnData(920,360).put("msgNum", desk.getComponent(DeskComponent.class).getDeskNum()));
        }

    }
    @Override
    protected void onCollisionEnd(Entity player, Entity desk) {
        if(player.getComponent(PlayerComponent.class).getPlayerNum() == 1){
            message1.removeFromWorld();
        }
        else{
            message2.removeFromWorld();
        }
    }
}
