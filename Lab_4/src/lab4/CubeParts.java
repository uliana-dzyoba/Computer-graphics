package lab4;

import com.sun.j3d.utils.geometry.*;

import javax.media.j3d.*;
import javax.vecmath.*;
import java.awt.*;

public class CubeParts {

    private static final float[] verts = {
            // front face
            1.0f, -1.0f,  1.0f,
            1.0f,  1.0f,  1.0f,
            -1.0f,  1.0f,  1.0f,
            -1.0f, -1.0f,  1.0f,
            // back face
            -1.0f, -1.0f, -1.0f,
            -1.0f,  1.0f, -1.0f,
            1.0f,  1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            // right face
            1.0f, -1.0f, -1.0f,
            1.0f,  1.0f, -1.0f,
            1.0f,  1.0f,  1.0f,
            1.0f, -1.0f,  1.0f,
            // left face
            -1.0f, -1.0f,  1.0f,
            -1.0f,  1.0f,  1.0f,
            -1.0f,  1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f,
            // top face
            1.0f,  1.0f,  1.0f,
            1.0f,  1.0f, -1.0f,
            -1.0f,  1.0f, -1.0f,
            -1.0f,  1.0f,  1.0f,
            // bottom face
            -1.0f, -1.0f,  1.0f,
            -1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, -1.0f,  1.0f,
    };


    private static Shape3D createShape(double scale, Color3f clrFront, Color3f clrBack, Color3f clrRight, Color3f clrLeft, Color3f clrTop, Color3f clrBottom) {

        final Color3f[] colors = new Color3f[]{clrFront, clrFront, clrFront, clrFront,
                clrBack, clrBack, clrBack, clrBack,
                clrRight, clrRight, clrRight, clrRight,
                clrLeft, clrLeft, clrLeft, clrLeft,
                clrTop, clrTop, clrTop, clrTop,
                clrBottom, clrBottom, clrBottom, clrBottom};

        QuadArray cube = new QuadArray(24, QuadArray.COORDINATES |
                QuadArray.COLOR_3);
        float scaledVerts[] = new float[verts.length];
        for (int i = 0; i < verts.length; i++)
            scaledVerts[i] = verts[i] * (float)scale;

        cube.setCoordinates(0, scaledVerts);

        cube.setColors(0, colors);

        final GeometryInfo gi = new GeometryInfo(cube);
        //also generate normal vectors so that the surface can be light
        final NormalGenerator normalGenerator = new NormalGenerator();
        normalGenerator.generateNormals(gi);

        final GeometryArray geometryArray = gi.getGeometryArray();
        return new Shape3D(geometryArray);
    }

    public static Node getElement(float size, Color3f clrFront, Color3f clrBack, Color3f clrRight, Color3f clrLeft, Color3f clrTop, Color3f clrBottom) {
        final Shape3D child = createShape(size, clrFront, clrBack, clrRight, clrLeft, clrTop, clrBottom);
        final Appearance fillAppNode = getElementAppearence();
        final PolygonAttributes pAtt = new PolygonAttributes();
        // avoid trouble with clockwise faces
        pAtt.setCullFace(PolygonAttributes.CULL_NONE);
        pAtt.setBackFaceNormalFlip(true);
        fillAppNode.setPolygonAttributes(pAtt);
        child.setAppearance(fillAppNode);

        return child;
    }

    public static Appearance getElementAppearence() {
        Appearance ap = new Appearance();
        Color3f specular = new Color3f(new Color(0,0, 0));
        Material material = new Material();
        material.setSpecularColor(specular);
        material.setShininess(1.0f);
        material.setColorTarget(Material.AMBIENT_AND_DIFFUSE);
        ap.setMaterial(material);
        return ap;
    }

}
