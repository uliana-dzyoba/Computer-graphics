package lab5;

import com.sun.j3d.utils.universe.*;
import java.awt.Color;
import javax.media.j3d.*;
import javax.media.j3d.Material;
import javax.swing.*;
import javax.vecmath.*;
import javax.media.j3d.Background;
import com.sun.j3d.loaders.*;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.image.TextureLoader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.URL;
import java.util.Map;

public class Main extends JFrame implements ActionListener, KeyListener {
    private final static String boatModelLocation = "Paper_Boat.obj";
    private final static String backgroundLocation = "river.png";
    private final BranchGroup root = new BranchGroup();
    private final Canvas3D canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
    private final TransformGroup boatGroup = new TransformGroup();
    private final Transform3D transform3D = new Transform3D();
    private final Transform3D rotateTransformY = new Transform3D();
    private final Transform3D rotateTransformZ = new Transform3D();
    private final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    private final Timer timer = new Timer(50, this);
    private SimpleUniverse universe;
    private Scene boatScene;
    private Background background;
    private Map<String, Shape3D> nameMap;

    private float x_location_current = -3;
    private float z_location_initial = -5;
    private float z_location_current = -4;
    float diff = 0.01f;
    int swing = 0;


    public static void main(String[] args) {
        try {
            Main window = new Main();
            window.addKeyListener(window);
            window.setVisible(true);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public Main() throws IOException {
        initialize();
        addTexture();
        addImageBackground();
        addLight();
        setInitialViewAngle();
        setInitialLocation();
        root.compile();
        universe.addBranchGraph(root);
    }

    private void setInitialLocation() {
        transform3D.setTranslation(new Vector3f(x_location_current, 0, 0));
        boatGroup.setTransform(transform3D);
    }

    private void initialize() throws IOException {
        // window settings
        setTitle("Lab #5");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        canvas.setDoubleBufferEnable(true);
        getContentPane().add(canvas, BorderLayout.CENTER);

        universe = new SimpleUniverse(canvas);
        universe.getViewingPlatform().setNominalViewingTransform();
        timer.start();
        canvas.addKeyListener(this);
        boatScene = getSceneFromFile();
    }

    private void addLight() {
        DirectionalLight dirLight = new DirectionalLight(
                new Color3f(Color.WHITE),
                new Vector3f(4.0f, -7.0f, -12.0f)
        );

        dirLight.setInfluencingBounds(new BoundingSphere(new Point3d(), 1000));
        root.addChild(dirLight);

        AmbientLight ambientLight = new AmbientLight(new Color3f(Color.WHITE));
        DirectionalLight directionalLight = new DirectionalLight(
                new Color3f(Color.BLACK),
                new Vector3f(-1F, -1F, -1F)
        );
        BoundingSphere influenceRegion = new BoundingSphere(new Point3d(), 1000);
        ambientLight.setInfluencingBounds(influenceRegion);
        directionalLight.setInfluencingBounds(influenceRegion);
        root.addChild(ambientLight);
        root.addChild(directionalLight);
    }

    private TextureLoader getTextureLoader(String path) throws IOException {
        URL textureResource = classLoader.getResource(path);
        if (textureResource == null) {
            throw new IOException("Couldn't find texture: " + path);
        }
        return new TextureLoader(textureResource.getPath(), canvas);
    }

    private void addTexture() throws IOException {
        Texture texture = getTextureLoader("paper3.jpg").getTexture();
        texture.setBoundaryModeS(Texture.WRAP);
        texture.setBoundaryModeT(Texture.WRAP);
        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.MODULATE);

        Appearance ap = new Appearance();
        ap.setTexture(texture);
        ap.setTextureAttributes(texAttr);

        nameMap = boatScene.getNamedObjects();
        System.out.println(nameMap.get("box001").getParent());
        BranchGroup parent = (BranchGroup)(nameMap.get("box001").getParent());
        parent.removeChild(nameMap.get("box001"));
        nameMap.get("box001").setAppearance(ap);
        boatGroup.addChild(nameMap.get("box001"));
        boatGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        root.addChild(boatGroup);

    }

    private void addImageBackground() throws IOException {
        background = new Background(getTextureLoader(backgroundLocation).getImage());
        background.setImageScaleMode(Background.SCALE_FIT_MAX);
        background.setApplicationBounds(new BoundingSphere(new Point3d(),1000));
        background.setCapability(Background.ALLOW_IMAGE_WRITE);
        root.addChild(background);
    }

    private void setInitialViewAngle() {
        ViewingPlatform vp = universe.getViewingPlatform();
        Transform3D transform = new Transform3D();
        transform.lookAt(
                new Point3d(-15f, 4f, 0f),
                new Point3d(-1f, 0, 0),
                new Vector3d(0f, 1, 0.1f)
        );
        transform.invert();
        vp.getViewPlatformTransform().setTransform(transform);
    }

    private Scene getSceneFromFile() throws IOException {
        ObjectFile file = new ObjectFile(ObjectFile.RESIZE);
        file.setFlags(ObjectFile.RESIZE | ObjectFile.TRIANGULATE | ObjectFile.STRIPIFY);
        InputStream inputStream = classLoader.getResourceAsStream(boatModelLocation);
        if (inputStream == null) {
            throw new IOException("Resource " + boatModelLocation + " not found");
        }
        return file.load(new BufferedReader(new InputStreamReader(inputStream)));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
        int keyCode = e.getKeyCode();
        float diff = 0.05f;

        switch (keyCode) {
            case KeyEvent.VK_LEFT: {
                x_location_current += 0.05;
            } break;
            case KeyEvent.VK_RIGHT: {
                x_location_current -= 0.05;
            } break;
            case KeyEvent.VK_A: {
                rotateTransformY.rotY(diff);
                transform3D.mul(rotateTransformY);
                boatGroup.setTransform(transform3D);
            } break;
            case KeyEvent.VK_S: {
                rotateTransformY.rotY(-diff);
                transform3D.mul(rotateTransformY);
                boatGroup.setTransform(transform3D);
            } break;
            case KeyEvent.VK_Z: {
                transform3D.setTranslation(new Vector3f(0, 0, z_location_initial));
                boatGroup.setTransform(transform3D);
                z_location_current = z_location_initial;
            } break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void actionPerformed(ActionEvent e) {

        transform3D.setTranslation(new Vector3f(x_location_current, 0, z_location_current));
        rotateTransformZ.rotZ(diff);
        transform3D.mul(rotateTransformZ);
        boatGroup.setTransform(transform3D);
        z_location_current += 0.05;
        swing++;
        if(swing == 10){
            diff *= -1;
            swing = 0;
        }
    }
}

