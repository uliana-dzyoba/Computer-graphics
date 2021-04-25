package lab4;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Cube extends Applet implements ActionListener {

    private final TransformGroup cubeTransformGroup = new TransformGroup();
    private final Transform3D cubeTransform3dx = new Transform3D();
    private final Transform3D cubeTransform3dy = new Transform3D();
    private final Transform3D cubeTransform3dz = new Transform3D();
    private final Transform3D cubeTransform3d = new Transform3D();
    private final Timer timer = new Timer(50, this);
    private float angle = 0;
    private float angleX = 0;
    private float angleY = 0;
    private double scale = 0;
    private boolean rotateY = true;
    private boolean isDecreasing = false;

    public static void main(String[] args) {
        Cube obj = new Cube();
        MainFrame mf = new MainFrame(obj, 700, 700);
        mf.run();
    }

    private Cube() {
        setLayout(new BorderLayout());
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        Canvas3D c = new Canvas3D(config);
        add("Center", c);
        SimpleUniverse universe = new SimpleUniverse(c);

        timer.start();
        universe.getViewingPlatform().setNominalViewingTransform();
        universe.addBranchGraph(createSceneGraph());
    }

    private BranchGroup createSceneGraph() {
        BranchGroup root = new BranchGroup();

        cubeTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        root.addChild(cubeTransformGroup);
        Color3f colorRed = new Color3f(new Color(100, 0, 15));
        Color3f colorOrange = new Color3f(new Color(100, 20, 0));
        Color3f colorYellow = new Color3f(new Color(100, 60, 0));
        Color3f colorGreen = new Color3f(new Color(0,45,30));
        Color3f colorBlue = new Color3f(new Color(0,0,80));
        Color3f colorWhite = new Color3f(new Color(105,105,105));
        Color3f colorGray = new Color3f(new Color(10,10,10));
        float indent = 0.307f;
        float firstX = -0.3f;
        float firstY = -0.3f;
        float firstZ = 0.3f;
        createElement(firstX, firstY, firstZ, colorGreen, colorGray, colorGray, colorOrange, colorGray, colorBlue);
        createElement(firstX+indent, firstY, firstZ, colorGreen, colorGray, colorGray, colorGray, colorGray, colorBlue);
        createElement(firstX+indent, firstY+indent, firstZ-2*indent, colorGray, colorYellow, colorGray, colorGray, colorGray, colorGray);
        createElement(firstX+indent, firstY+2*indent, firstZ-2*indent, colorGray, colorYellow, colorGray, colorGray, colorWhite, colorGray);
        createElement(firstX+indent, firstY+2*indent, firstZ-indent, colorGray, colorGray, colorGray, colorGray, colorWhite, colorGray);
        createElement(firstX+indent, firstY+2*indent, firstZ, colorGreen, colorGray, colorGray, colorGray, colorWhite, colorGray);//
        createElement(firstX+indent, firstY+indent, firstZ, colorGreen, colorGray, colorGray, colorGray, colorGray, colorGray);//
        createElement(firstX+2*indent, firstY, firstZ, colorGreen, colorGray, colorRed, colorGray, colorGray, colorBlue);
        createElement(firstX+2*indent, firstY+indent, firstZ, colorGreen, colorGray, colorRed, colorGray, colorGray, colorGray);
        createElement(firstX+2*indent, firstY+2*indent, firstZ, colorGreen, colorGray, colorRed, colorGray, colorWhite, colorGray);
        createElement(firstX+2*indent, firstY+indent, firstZ-indent, colorGray, colorGray, colorRed, colorGray, colorGray, colorGray);
        createElement(firstX+2*indent, firstY+2*indent, firstZ-indent, colorGray, colorGray, colorRed, colorGray, colorWhite, colorGray);
        createElement(firstX+2*indent, firstY+indent, firstZ-2*indent, colorGray, colorYellow, colorRed, colorGray, colorGray, colorGray);
        createElement(firstX+2*indent, firstY+2*indent, firstZ-2*indent, colorGray, colorYellow, colorRed, colorGray, colorWhite, colorGray);
        createElement(firstX, firstY, firstZ-indent, colorGray, colorGray, colorGray, colorOrange, colorGray, colorBlue);
        createElement(firstX+indent, firstY, firstZ-indent, colorGray, colorGray, colorGray, colorGray, colorGray, colorBlue);
        createElement(firstX+2*indent, firstY, firstZ-indent, colorGray, colorGray, colorRed, colorGray, colorGray, colorBlue);
        createElement(firstX, firstY, firstZ-2*indent, colorGray, colorYellow, colorGray, colorOrange, colorGray, colorBlue);
        createElement(firstX+indent, firstY, firstZ-2*indent, colorGray, colorYellow, colorGray, colorGray, colorGray, colorBlue);
        createElement(firstX+2*indent, firstY, firstZ-2*indent, colorGray, colorYellow, colorRed, colorGray, colorGray, colorBlue);
        createElement(firstX, firstY+indent, firstZ, colorGreen, colorGray, colorGray, colorOrange, colorGray, colorGray);
        createElement(firstX, firstY+2*indent, firstZ, colorGreen, colorGray, colorGray, colorOrange, colorWhite, colorGray);
        createElement(firstX, firstY+indent, firstZ-indent, colorGray, colorGray, colorGray, colorOrange, colorGray, colorGray);
        createElement(firstX, firstY+2*indent, firstZ-indent, colorGray, colorGray, colorGray, colorOrange, colorWhite, colorGray);
        createElement(firstX, firstY+indent, firstZ-2*indent, colorGray, colorYellow, colorGray, colorOrange, colorGray, colorGray);
        createElement(firstX, firstY+2*indent, firstZ-2*indent, colorGray, colorYellow, colorGray, colorOrange, colorWhite, colorGray);

        // light section start
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),100);

        //Color sunLightColor = new Color(242, 255, 0);
        Color sunLightColor = new Color(255, 255, 255);
        DirectionalLight lightDirect = new DirectionalLight(new Color3f(sunLightColor), new Vector3f(4.0f, -7.0f, -12.0f));
        lightDirect.setInfluencingBounds(bounds);
        root.addChild(lightDirect);

        AmbientLight ambientLightNode = new AmbientLight(new Color3f(new Color(255, 255, 255)));
        ambientLightNode.setInfluencingBounds(bounds);
        root.addChild(ambientLightNode);
        // light section end

        return root;
    }

    private void createElement(float x, float y, float z, Color3f clrFront, Color3f clrBack, Color3f clrRight, Color3f clrLeft, Color3f clrTop, Color3f clrBottom) {
        TransformGroup tg = new TransformGroup();
        Transform3D transform = new Transform3D();
        Node cube = CubeParts.getElement(0.15f, clrFront, clrBack, clrRight, clrLeft, clrTop, clrBottom);
        Vector3f vector = new Vector3f(x, y, z);
        transform.setTranslation(vector);
        tg.setTransform(transform);
        tg.addChild(cube);
        cubeTransformGroup.addChild(tg);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        cubeTransform3dx.rotX(angleX);
        cubeTransform3dy.rotY(angleY);
        cubeTransform3d.mul(cubeTransform3dx, cubeTransform3dy);
        angleX += 0.05;
        angleY += 0.05;
        angle += 0.05;
        if (angle < 6.283) {
            angleX = 0;
        }
        if (angle > 6.283 && angle < 12.566) {
            angleY = 0;
        }

        cubeTransformGroup.setTransform(cubeTransform3d);
    }
}
