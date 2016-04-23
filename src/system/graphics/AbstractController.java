package system.graphics;

public abstract class AbstractController {
    protected CustomScene scene;

    public void initData(CustomScene scene) {
        this.scene = scene;
    }

    public abstract void prepareToDisplay(Scenetype prevSceneType);
}
