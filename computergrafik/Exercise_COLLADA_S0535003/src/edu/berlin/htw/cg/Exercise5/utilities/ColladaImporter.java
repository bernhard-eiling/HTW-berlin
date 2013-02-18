package edu.berlin.htw.cg.Exercise5.utilities;

/**
 * Copyright (c) 2008-2010 Ardor Labs, Inc.
 *
 * This file is part of Ardor3D.
 *
 * Ardor3D is free software: you can redistribute it and/or modify it 
 * under the terms of its license which may be found in the accompanying
 * LICENSE file or at <http://www.ardor3d.com/LICENSE>.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.media.opengl.GL2;

import org.jdom.Attribute;
import org.jdom.DataConversionException;
import org.jdom.DefaultJDOMFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.Text;
import org.jdom.filter.ElementFilter;
import org.jdom.input.SAXBuilder;
import org.jdom.input.SAXHandler;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.xml.sax.SAXException;

import com.google.common.collect.Maps;

import edu.berlin.htw.cg.Exercise5.interfaces.CGAnimatedNode;
import edu.berlin.htw.cg.Exercise5.interfaces.CGAnimationChannel;
import edu.berlin.htw.cg.Exercise5.interfaces.CGMesh;
import edu.berlin.htw.cg.Exercise5.interfaces.CGMesh.CGMaterial;
import edu.berlin.htw.cg.Exercise5.interfaces.CGNodeSkinned;
import edu.berlin.htw.cg.Exercise5.interfaces.CGObjectFactory;
import edu.berlin.htw.cg.Exercise5.interfaces.CGScene;
import edu.berlin.htw.cg.Exercise5.interfaces.CGSceneNode;
import edu.berlin.htw.cg.Exercise5.interfaces.Matrix4f;
import edu.berlin.htw.cg.Exercise5.interfaces.Vector3f;
import edu.berlin.htw.cg.Exercise5.tests.DSObjectFactoryImpl;

/**
 * Main class for importing Collada files.
 * <p>
 * Example usages:
 * <li>new ColladaImporter().load(resource);</li>
 * <li>new ColladaImporter().loadTextures(false).modelLocator(locator).load(resource);</li>
 * </p>
 */
public class ColladaImporter {
    private boolean _loadTextures = true;
    private boolean _flipTransparency = false;
    private boolean _loadAnimations = true;
//    private ResourceLocator _textureLocator;
//    private ResourceLocator _modelLocator;
    private boolean _compressTextures = false;
    private DataCache cache;
	private CGObjectFactory factory;
	private static Element root;
	public static final Map<String,CGMesh> MESHES = new HashMap<String,CGMesh>();
	public static final Map<String,int[]> MESH_IDXMAP = new HashMap<String,int[]>();
	public static final Set<String> AnimatedNodeNames = new TreeSet<String>();
	private static final ColladaImporter IMPORTER = new ColladaImporter();
    private static String modelpath = null;
    
    public ColladaImporter() {
    	cache = new DataCache();

    }
    
    public static CGObjectFactory factory(){return IMPORTER.factory;};
    
    public static class DataCache{
        private final Map<Element, float[]> _floatArrays;
        private final Map<Element, double[]> _doubleArrays;
        private final Map<Element, boolean[]> _booleanArrays;
        private final Map<Element, int[]> _intArrays;
        private final Map<Element, String[]> _stringArrays;

        private final Map<String, Element> _idCache;
        private final Map<String, Element> _sidCache;

        public DataCache(){
            _floatArrays = Maps.newHashMap();
            _doubleArrays = Maps.newHashMap();
            _booleanArrays = Maps.newHashMap();
            _intArrays = Maps.newHashMap();
            _stringArrays = Maps.newHashMap();
        	
            _idCache = Maps.newHashMap();
            _sidCache = Maps.newHashMap();
        }

        public Map<String, Element> getIdCache() {
            return _idCache;
        }

        public Map<String, Element> getSidCache() {
            return _sidCache;
        }

        public Map<Element, float[]> getFloatArrays() {
            return _floatArrays;
        }

        public Map<Element, double[]> getDoubleArrays() {
            return _doubleArrays;
        }

        public Map<Element, boolean[]> getBooleanArrays() {
            return _booleanArrays;
        }

        public Map<Element, int[]> getIntArrays() {
            return _intArrays;
        }

        public Map<Element, String[]> getStringArrays() {
            return _stringArrays;
        }

    }
    
    public ColladaImporter setLoadTextures(final boolean loadTextures) {
        _loadTextures = loadTextures;
        return this;
    }

    public ColladaImporter setCompressTextures(final boolean compressTextures) {
        _compressTextures = compressTextures;
        return this;
    }

    public ColladaImporter setLoadAnimations(final boolean loadAnimations) {
        _loadAnimations = loadAnimations;
        return this;
    }

    /**
     * @param flipTransparency
     *            if true, invert the value of any "transparency" entries found - required for some exporters.
     * @return this importer, for chaining
     */
    public ColladaImporter setFlipTransparency(final boolean flipTransparency) {
        _flipTransparency = flipTransparency;
        return this;
    }

//    public ColladaImporter setTextureLocator(final ResourceLocator textureLocator) {
//        _textureLocator = textureLocator;
//        return this;
//    }
//
//    public ColladaImporter setModelLocator(final ResourceLocator modelLocator) {
//        _modelLocator = modelLocator;
//        return this;
//    }
//
//    /**
//     * Reads a Collada file from the given resource and returns it as a ColladaStorage object.
//     * 
//     * @param resource
//     *            the name of the resource to find. ResourceLocatorTool will be used with TYPE_MODEL to find the
//     *            resource.
//     * @return a ColladaStorage data object containing the Collada scene and other useful elements.
//     */
//    public ColladaStorage load(final String resource) {
//        final ResourceSource source;
//        if (_modelLocator == null) {
//            source = ResourceLocatorTool.locateResource(ResourceLocatorTool.TYPE_MODEL, resource);
//        } else {
//            source = _modelLocator.locateResource(resource);
//        }
//
//        if (source == null) {
//            throw new Error("Unable to locate '" + resource + "'");
//        }
//
//        return load(source);
//
//    }
//
//    /**
//     * Reads a Collada file from the given resource and returns it as a ColladaStorage object.
//     * 
//     * @param resource
//     *            the name of the resource to find.
//     * @return a ColladaStorage data object containing the Collada scene and other useful elements.
//     */
//    public ColladaStorage load(final ResourceSource resource) {
//        final ColladaStorage colladaStorage = new ColladaStorage();
//        final DataCache dataCache = new DataCache();
//        final ColladaDOMUtil colladaDOMUtil = new ColladaDOMUtil(dataCache);
//        final ColladaMaterialUtils colladaMaterialUtils = new ColladaMaterialUtils(_loadTextures, dataCache,
//                colladaDOMUtil, _textureLocator, _compressTextures, _flipTransparency);
//        final ColladaMeshUtils colladaMeshUtils = new ColladaMeshUtils(dataCache, colladaDOMUtil, colladaMaterialUtils);
//        final ColladaAnimUtils colladaAnimUtils = new ColladaAnimUtils(colladaStorage, dataCache, colladaDOMUtil,
//                colladaMeshUtils);
//        final ColladaNodeUtils colladaNodeUtils = new ColladaNodeUtils(dataCache, colladaDOMUtil, colladaMaterialUtils,
//                colladaMeshUtils, colladaAnimUtils);
//
//        try {
//
//            // Pull in the DOM tree of the Collada resource.
//            final Element collada = readCollada(resource, dataCache);
//
//            // if we don't specify a texture locator, add a temporary texture locator at the location of this model
//            // resource..
//            final boolean addLocator = _textureLocator == null;
//
//            final RelativeResourceLocator loc;
//            if (addLocator) {
//                loc = new RelativeResourceLocator(resource);
//                ResourceLocatorTool.addResourceLocator(ResourceLocatorTool.TYPE_TEXTURE, loc);
//            } else {
//                loc = null;
//            }
//
//            final AssetData assetData = colladaNodeUtils.parseAsset(collada.getChild("asset"));
//
//            // Collada may or may not have a scene, so this can return null.
//            final Node scene = colladaNodeUtils.getVisualScene(collada);
//
//            if (_loadAnimations) {
//                colladaAnimUtils.parseLibraryAnimations(collada);
//            }
//
//            // set our scene into storage
//            colladaStorage.setScene(scene);
//
//            // set our asset data into storage
//            colladaStorage.setAssetData(assetData);
//
//            // drop our added locator if needed.
//            if (addLocator) {
//                ResourceLocatorTool.removeResourceLocator(ResourceLocatorTool.TYPE_TEXTURE, loc);
//            }
//
//            // return storage
//            return colladaStorage;
//        } catch (final Exception e) {
//            throw new Error("Unable to load collada resource from URL: " + resource, e);
//        }
//    }
    private Element readCollada(final InputStream resource) {
    	return readCollada(resource, cache);
    }

    /**
     * Reads the whole Collada DOM tree from the given resource and returns its root element. Exceptions may be thrown
     * by underlying tools; these will be wrapped in a RuntimeException and rethrown.
     * 
     * @param resource
     *            the ResourceSource to read the resource from
     * @return the Collada root element
     */
    private Element readCollada(final InputStream resource, final DataCache dataCache) {
        try {
            final SAXBuilder builder = new SAXBuilder() {
                @Override
                protected SAXHandler createContentHandler() {
                    return new SAXHandler(new ArdorFactory(dataCache)) {
                        @Override
                        public void startPrefixMapping(final String prefix, final String uri) throws SAXException {
                        // Just kill what's usually done here...
                        }

                    };
                }
            };

            final Document doc = builder.build(resource);
            final Element collada = doc.getRootElement();

            // ColladaDOMUtil.stripNamespace(collada);

            return collada;
        } catch (final Exception e) {
            throw new RuntimeException("Unable to load collada resource from source: " + resource, e);
        }
    }

    private enum BufferType {
        None, Float, Double, Int, String, P
    }

    /**
     * A JDOMFactory that normalizes all text (strips extra whitespace etc), preparses all arrays and hashes all
     * elements based on their id/sid.
     */
    private static final class ArdorFactory extends DefaultJDOMFactory {
        private final DataCache dataCache;
        private Element currentElement;
        private BufferType bufferType = BufferType.None;
        private int count = 0;
        private final List<String> list = new ArrayList<String>();

        ArdorFactory(final DataCache dataCache) {
            this.dataCache = dataCache;
        }

        @Override
        public Text text(final String text){
            try {
                switch (bufferType) {
                    case Float: {
                        final String normalizedText = Text.normalizeString(text);
                        if (normalizedText.length() == 0) {
                            return new Text("");
                        }
                        final StringTokenizer tokenizer = new StringTokenizer(normalizedText, " ");
                        final float[] floatArray = new float[count];
                        for (int i = 0; i < count; i++) {
                            floatArray[i] = Float.parseFloat(tokenizer.nextToken().replace(",", "."));
                        }

                        dataCache.getFloatArrays().put(currentElement, floatArray);

                        return new Text("");
                    }
                    case Double: {
                        final String normalizedText = Text.normalizeString(text);
                        if (normalizedText.length() == 0) {
                            return new Text("");
                        }
                        final StringTokenizer tokenizer = new StringTokenizer(normalizedText, " ");
                        final double[] doubleArray = new double[count];
                        for (int i = 0; i < count; i++) {
                            doubleArray[i] = Double.parseDouble(tokenizer.nextToken().replace(",", "."));
                        }

                        dataCache.getDoubleArrays().put(currentElement, doubleArray);

                        return new Text("");
                    }
                    case Int: {
                        final String normalizedText = Text.normalizeString(text);
                        if (normalizedText.length() == 0) {
                            return new Text("");
                        }
                        final StringTokenizer tokenizer = new StringTokenizer(normalizedText, " ");
                        final int[] intArray = new int[count];
                        int i = 0;
                        while (tokenizer.hasMoreTokens()) {
                            intArray[i++] = Integer.parseInt(tokenizer.nextToken());
                        }

                        dataCache.getIntArrays().put(currentElement, intArray);

                        return new Text("");
                    }
                    case P: {
                        list.clear();
                        final String normalizedText = Text.normalizeString(text);
                        if (normalizedText.length() == 0) {
                            return new Text("");
                        }
                        final StringTokenizer tokenizer = new StringTokenizer(normalizedText, " ");
                        while (tokenizer.hasMoreTokens()) {
                            list.add(tokenizer.nextToken());
                        }
                        final int listSize = list.size();
                        final int[] intArray = new int[listSize];
                        for (int i = 0; i < listSize; i++) {
                            intArray[i] = Integer.parseInt(list.get(i));
                        }

                        dataCache.getIntArrays().put(currentElement, intArray);

                        return new Text("");
                    }
                }
            } catch (final NoSuchElementException e) {
                throw new Error("Number of values in collada array does not match its count attribute: "
                        + count, e);
            }
            return new Text(Text.normalizeString(text));
        }

        @Override
        public void setAttribute(final Element parent, final Attribute a) {
            if ("id".equals(a.getName())) {
                dataCache.getIdCache().put(a.getValue(), parent);
            } else if ("sid".equals(a.getName())) {
                dataCache.getSidCache().put(a.getValue(), parent);
            } else if ("count".equals(a.getName())) {
                try {
                    count = a.getIntValue();
                } catch (final DataConversionException e) {
                    e.printStackTrace();
                }
            }

            super.setAttribute(parent, a);
        }

        @Override
        public Element element(final String name, final Namespace namespace) {
            currentElement = super.element(name);
            handleTypes(name);
            return currentElement;
        }

        @Override
        public Element element(final String name, final String prefix, final String uri) {
            currentElement = super.element(name);
            handleTypes(name);
            return currentElement;
        }

        @Override
        public Element element(final String name, final String uri) {
            currentElement = super.element(name);
            handleTypes(name);
            return currentElement;
        }

        @Override
        public Element element(final String name) {
            currentElement = super.element(name);
            handleTypes(name);
            return currentElement;
        }

        private void handleTypes(final String name) {
            if ("float_array".equals(name)) {
                bufferType = BufferType.Float;
            } else if ("double_array".equals(name)) {
                bufferType = BufferType.Double;
            } else if ("int_array".equals(name)) {
                bufferType = BufferType.Int;
            } else if ("p".equals(name)) {
                bufferType = BufferType.P;
            } else {
                bufferType = BufferType.None;
            }
        }
    }
    private class SetMember implements Comparable<SetMember>{
    	public int [] v = new int[6];

		@Override
		public int compareTo(SetMember o) {
			int ret = -1;
			if(v[0]==o.v[0] && v[1]==o.v[1] && v[2]==o.v[2] && v[3]==o.v[3] ) ret= 0;
			if(v[0]<o.v[0] ) ret = 1;
			else if(v[0]>o.v[0]) ret = -1;
			else if(v[1]<o.v[1]) ret = 1;
			else if(v[1]>o.v[1])ret = -1;
			else if(v[2]<o.v[2]) ret = 1;
			else if(v[2]>o.v[2])ret = -1;
			else if(v[3]<o.v[3]) ret = 1;
			else if(v[3]>o.v[3])ret = -1;
			else ret = 0;
			return ret;
		}
    	
    }

    public void readMesh(Element mesh, String name){
    	//Merge different mesh part... for now
    	//TODO: Material settings for mesh parts
       	iterateMeshTypes(mesh, name, "triangles", GL2.GL_TRIANGLES, false);
       	iterateMeshTypes(mesh, name, "polylist", GL2.GL_QUADS, false);
       	iterateMeshTypes(mesh, name, "polygons", GL2.GL_QUADS, true);
    }
    
    public void iterateMeshTypes(Element mesh, String name, String type, int drawtype, boolean testEntries){
    	int index = 0;
       	List<Element> polys = (List<Element>)mesh.getChildren(type);
       	for(Element poly : polys){
    		CGMesh res_mesh = IMPORTER.factory.createMesh();
    		//in this case: Please draw this as quads!
    		// this is only a guess will find out precisely later
    		res_mesh.setDrawType(drawtype);
    		String id = index == 0 ? name : name + "_" + type + (++index);
     		if(readMeshPart(id, testEntries, poly, mesh, res_mesh)){
        		IMPORTER.checkMaterials(mesh, res_mesh);
        		MESHES.put(id, res_mesh);
    		}
       	
       	}
    }
    
	List<String> inputNames = new ArrayList<String>();
	List<float[]> values = new ArrayList<float[]>();
	List<Integer> dataSize = new ArrayList<Integer>();
	List<Integer> offsets = new ArrayList<Integer>();
    
    private void addDataArray(String name, Element input, int offset, int stride ){
		String posS = input.getAttributeValue("source").substring(1);
		Element poss = cache.getIdCache().get(posS);
		Element farray = poss.getChild("float_array");
    	float[] posa = cache.getFloatArrays().get(farray);
    	inputNames.add(name);
    	offsets.add(offset);
    	values.add(posa);
    	dataSize.add(stride);
    }
    
    public boolean readMeshPart(String id, boolean testPolyEntries, Element tris, Element mesh, CGMesh res_mesh){
    	boolean success = true;
    	
     	
    	checkMaterials(tris, res_mesh);
    	
    	List inputs = tris.getChildren("input");
    	int droppedInputs = inputs.size(); //Assume nothing gets loaded
    	int additionalInputs = 0;
    	
    	inputNames.clear();
    	values.clear();
    	dataSize.clear();
    	offsets.clear();
    	
    	int stride = 0;//maximum found offset != # of inputs: two inp might have same offset!
    	int idx = 0;    	
    	//get input element
    	for(Object inpO : inputs){
    		Element inp = (Element)inpO;
    		int offset = Integer.parseInt(inp.getAttributeValue("offset"));
    		stride = Math.max(stride, offset);
    		
    		if(inp.getAttributeValue("semantic").equalsIgnoreCase("VERTEX")){
	        	//if input Semantic=VERTEX --> name of the vertices source
    			String vertS = inp.getAttributeValue("source").substring(1);
    	    	Element verts = cache.getIdCache().get(vertS);
    	    	//get input element
    	    	for(Object inpO2 : verts.getChildren("input")){
    	    		Element inp2 = (Element)inpO2;
    	    		if(inp2.getAttributeValue("semantic").equalsIgnoreCase("POSITION")){
    		        	//if input Semantic=POSITIONS --> just a list of vertices
    		        	// get input source attr, and element of that name
    		        	//get float_array child of the element
    	    			addDataArray("POSITION",inp2, offset,3);
    		        	// technique is supposed to be X,Y,Z
    		        	//break;
    	    		} else if(inp2.getAttributeValue("semantic").equalsIgnoreCase("NORMAL")){
    	    			addDataArray("NORMAL",inp2, offset,3);
    		        	additionalInputs += 1; //this is an additional input not in original list
    	    		} else if(inp2.getAttributeValue("semantic").equalsIgnoreCase("TEXCOORD")){
    	    			addDataArray("TEXCOORD",inp2, offset,2);
    		        	additionalInputs += 1; //this is an additional input not in original list
    	    		}
    	    	}
    	    	droppedInputs--;
    		}else
    	    if(inp.getAttributeValue("semantic").equalsIgnoreCase("COLOR")){
    			addDataArray("COLOR",inp, offset,3);
	        	droppedInputs--;
    	  	}else
    	    if(inp.getAttributeValue("semantic").equalsIgnoreCase("NORMAL")){
    			addDataArray("NORMAL",inp, offset,3);
	        	droppedInputs--;
    	  	}else
        	    if(inp.getAttributeValue("semantic").equalsIgnoreCase("TEXCOORD")){
        			addDataArray("TEXCOORD",inp, offset,2);
    	        	droppedInputs--;
    	    }
    	}
    		
    	stride++; // stride is the maximum offset plus one
    	int nondropped = inputs.size()-droppedInputs;
    	int count = Integer.parseInt(tris.getAttributeValue("count"));
    	//Need to change that.
    	//Just take the indices from the vertices and rearrange the actual data 
    	//on the other arrays to get ONE index being the same on all arrays, otherwise
    	//glDrawElements won't work!
    	// Each combination of indices is a unique value that needs to be recreated. 
    	// Worst case is: All arrays are as large as the set of indices, but if vertices 
    	// with a given combination show up more than once it can be re-used
    	// Solution: Put all triple/quadruple combination into one set. 
    	//  Serialize the set and build arrays while doing so
    	
    	// Check for polycount, take the first entry as 
    	// master, all entries with other count are dismissed..
    	Element indc = tris.getChild("p");
		int[] firstPoly = cache.getIntArrays().get(indc);
    	int pcount = firstPoly.length/stride;
    	List<Element> indcs = tris.getChildren("p");
    	ArrayList<Integer> merge = new ArrayList<Integer>();
    	for(Element in : indcs) {
    		int[] onee = cache.getIntArrays().get(in);
    		if((testPolyEntries)&&(onee.length != stride*pcount)){
    			//This is not a quad, most likely a tri
    			System.out.println("Wrong length: " + onee.length);
    			count--; //throw this away --> better TRIANGULTE everything
    		}else{
        		for(int i =0; i< onee.length; i++) merge.add(onee[i]);
    		}
    	}
    	Integer [] indices = new Integer[merge.size()];
    	merge.toArray(indices);
    	int perElementCount = indices.length/(stride*count);

    	switch(perElementCount){
    	case 3 : res_mesh.setDrawType(GL2.GL_TRIANGLES);break;
    	case 4 : res_mesh.setDrawType(GL2.GL_QUADS);break;
    	default: System.out.println("Element count of mesh: " + perElementCount + " not supported, mesh skipped"); return false;
    	}
    	Set<SetMember> set = new TreeSet<SetMember>();
    	List<SetMember> setlist = new ArrayList<SetMember>();

    	for(int i = 0; i < count*perElementCount; i++){
    		SetMember elem = new SetMember();
    		for(int j = 0; j<nondropped; j++){
    			elem.v[j] = indices[i*stride + j];
    		}
    		set.add(elem);
    		setlist.add(elem);
    	}
    	//Where in setMember is position
    	int positionIndex = 0;
    	
    	System.out.println("SetSize; " + set.size() + "  vs. Elems: " + count*perElementCount);
    	// Now create arrays with fitting size and indices
		for(int j = 0; j<offsets.size(); j++){
			String name = inputNames.get(j);
			float[] vals = values.get(j);
			float[] newVals = new float[set.size()*3]; //XYZ RGB whatever
			int i = 0;
			int size =  dataSize.get(j); //Is it 2,3 or even 4 floats to be transfered for each index
			
			for(SetMember mem : set){
				for(int k = 0; k < size; k++)newVals[size*i + k] = vals[mem.v[offsets.get(j)]*size + k ];
				i++;
			}
			//Set corresponding member in mesh
			if(name.equalsIgnoreCase("POSITION")){
				res_mesh.setVertices(newVals);
				positionIndex = j;
			}
			else if(name.equalsIgnoreCase("NORMAL"))res_mesh.setNormals(newVals);
			else if(name.equalsIgnoreCase("COLOR"))res_mesh.setColors(newVals);
			else if(name.equalsIgnoreCase("TEXCOORD"))res_mesh.setUVCoords(newVals);
		}

		int i = 0;
		Map<SetMember,Integer> mix = new TreeMap<SetMember, Integer>();
		for(SetMember mem : set){
			mix.put(mem, i);
			i++;
		}
		
		int[] newInds = new int[count*perElementCount];
		// setList[i] == Original item on i --> mix maps that to the new index that was written
		for(i = 0; i< count*perElementCount; i++)newInds[i]= mix.get(setlist.get(i));
    	res_mesh.setIndices(newInds);
    	//for all NEW vertices 0...n store the OLD index in array
    	int[] indexMap = new int[set.size()];
    	i=0;
    	//ASSUMING positionIndex is the vertices index??
		for(SetMember mem : set){
			indexMap[i] = mem.v[positionIndex];
			i++;
		}
		MESH_IDXMAP.put(id,indexMap);
    	return success;
    }
    
    private Element findID(Element root, String id){
    	Element res = null;
    	for(Object o : root.getChildren()){
    		Element e = (Element)o;
    		String eid = e.getAttributeValue("name");
    		if(e.getAttributeValue("id") != null) eid = e.getAttributeValue("id");
    		if(eid.equals(id))return e;
    	}
    	return res;
    }
    
    private void readChannel(CGScene scene, Element root, String name){
		List chans = root.getChildren("channel");
		for(Object chanO : chans){
			Element chan = (Element)chanO;
    		String target = chan.getAttributeValue("target");
    		CGAnimationChannel dschan = IMPORTER.factory.createAnimationChannel();
    		dschan.setName(name);
    		
    		String boneid = new String(target.substring(0,target.lastIndexOf('/')));
    		String trans = new String(target.substring(target.lastIndexOf('/')+1));
    		
    		dschan.setTransformation(boneid, trans);
    		CGAnimatedNode trg = null;
    		try {
    			trg =(CGAnimatedNode)(scene.getRoot().getChildNode(dschan.getTargetName()));	
			} catch (ClassCastException e) {
				System.err.println("Cannot cast Node " + dschan.getTargetName() + " to AnimatedNode");
			}
    		dschan.setTarget(trg);
    		if(trg != null) trg.addAnimationChannel(dschan);
    		//Take sampler, extract Timings and data
    		String samplerID = chan.getAttributeValue("source");
    		Element sampl = root.getChild("sampler");
    		String sampIN="", sampOUT="";
    		for(Object o : sampl.getChildren("input")){
    			Element inp = (Element)o;
    			String type = inp.getAttributeValue("semantic");
    			if(type.equalsIgnoreCase("INPUT")){
    				sampIN = inp.getAttributeValue("source");
    				if(sampIN.startsWith("#"))sampIN = sampIN.substring(1);
    			}else if(type.equalsIgnoreCase("OUTPUT")){
    				sampOUT = inp.getAttributeValue("source");
    				if(sampOUT.startsWith("#"))sampOUT = sampOUT.substring(1);
    			}// we ignore interpolation type; assume linear
    		}

    		//find data sources to these names
    		FloatBuffer times=null, data=null;
    		int size=0, stride=0;
    		for(Object o : root.getChildren("source")){
    			Element inp = (Element)o;
    			String id = inp.getAttributeValue("id");
    			if(id.equalsIgnoreCase(sampIN)){
    				Element numbers = inp.getChild("float_array");
    				size = Integer.parseInt(numbers.getAttributeValue("count"));
		        	float[] posa = cache.getFloatArrays().get(numbers);
		        	times = FloatBuffer.wrap(posa);
    			}else if(id.equalsIgnoreCase(sampOUT)){
       				Element numbers = inp.getChild("float_array");
    				stride = Integer.parseInt(numbers.getAttributeValue("count"))/size;
		        	float[] posa = cache.getFloatArrays().get(numbers);
		        	data = FloatBuffer.wrap(posa);
    			}// we ignore interpolation type; assume linear
    		}
    		dschan.setKeys(size, stride, times, data);
    		scene.addAnimation(dschan);
			
		}
    }
    
    private void readAnimatedNodeNames(Element nodes){
		if(nodes == null) return;
		
    	for(Object nodeO : nodes.getChildren("animation")){
    		Element node = (Element)nodeO;
    		List chans = node.getChildren("channel");
    		for(Object chanO : chans){
    			Element chan = (Element)chanO;
    			String target = chan.getAttributeValue("target");
    			String boneid = new String(target.substring(0,target.lastIndexOf('/')));
    			AnimatedNodeNames.add(boneid);
    		}    		
			readAnimatedNodeNames(node);
    	}
    }
 
    private void readAnimations(Element nodes, CGScene scene){
		if(nodes == null) return;
		
    	for(Object nodeO : nodes.getChildren("animation")){
    		Element node = (Element)nodeO;
    		String id = node.getAttributeValue("id");
    		readChannel(scene, node, id);
    		//do it recursive
    		readAnimations(node, scene);
    	}
    }
    private Element getChild(Element el, String[] hier){
    	Element erg = null;
    	for (String name : hier) {
    		erg = el.getChild(name);
    		if(erg == null) return null;
    		else el = erg;
    	}
    	return erg;
    }
    private String checkMaterialAlias(Element mesh, String mat){
		Element nodes = root.getChild("library_visual_scenes").getChild("visual_scene");
    	for(Object nodeO : nodes.getChildren("node")){
    		Element node = (Element)nodeO;
    		List geom = node.getContent(new ElementFilter());
    		Element meshmat = getChild(node,new String[]{"instance_geometry","bind_material","technique_common","instance_material"});
    		if(meshmat != null){
    			String sym = meshmat.getAttribute("symbol").getValue();
    			String alis = meshmat.getAttribute("target").getValue();
    			if(alis.startsWith("#")) alis = alis.substring(1);
    			return alis;
    		}
    	}
    	return mat;
    }
    private float[] getLightParams(String lightName){
    	float[] res = new float[4];
		Element lights = root.getChild("library_lights");
		Element material = findID(lights, lightName);
		if(material != null){
			Element col = getChild(material, new String[]{"technique_common","point","color"});
			if(col != null) {
				float[] cols =  parse4floats(col.getContent(0).getValue());
				res[1] = cols[0];
				res[2] = cols[1];
				res[3] = cols[2];
				Element inte = getChild(material, new String[]{"technique","intensity"});
				if(inte != null) {
					float[] intensity = parse4floats(inte.getContent(0).getValue());
					res[0] = intensity[0];
				}
			}			
		}
		return res;
    }
    private Vector3f setTextureOrColor(Element elem, CGMesh mesh, Element efx){
    	Vector3f target = null;
		Element imgs = root.getChild("library_images");
		List<Element> nodes = elem.getChildren();
		for(Element child : nodes){
			String type = child.getName();
			if("color".equals(type)){
				float[] vals = parse4floats(child.getContent(0).getValue());
				target = IMPORTER.factory.createVector();
				target.set(vals[0], vals[1], vals[2]);
			} else if("texture".equals(type)){
				String imageN = child.getAttributeValue("texture");
				Element imgf = findID(imgs,imageN);
				if(imgf == null){
					imageN = getChild(efx, new String[]{"profile_COMMON","newparam","surface","init_from"}).getValue();
					imgf = findID(imgs,imageN);
				}
				String filename = imgf.getChild("init_from").getValue();
				if(filename.startsWith("file:"))filename = filename.substring(7);
				if(filename.startsWith(".")){
					filename = modelpath + filename;
					// relative filename --> is now relative to project directory, not model.dae file
				}
				mesh.setTextureSource(0, filename);
			}
		}
    	return target;
    }

    private void getMaterial(String mat, CGMesh mesh){
		Element mats = root.getChild("library_materials");
		Element efxs = root.getChild("library_effects");
		Element material = findID(mats, mat);
		if(material != null){
			CGMaterial res_mat = new CGMaterial();
			Element efx = material.getChild("instance_effect");
			String effect = efx.getAttributeValue("url").substring(1);
			efx = findID(efxs,effect);
			Element shader = getChild(efx, new String[]{"profile_COMMON","technique","lambert"});
			if(shader != null)res_mat.isLambert = true;
			else shader =  getChild(efx, new String[]{"profile_COMMON","technique","phong"});
			if(shader == null) shader =  getChild(efx, new String[]{"profile_COMMON","technique","blinn"});
			if(shader != null) {
				List<Element> nodes = shader.getChildren();
				for(Element child : nodes){
					String type = child.getName();
					if("ambient".equals(type)){
						res_mat.ambient = setTextureOrColor(child, mesh, efx);
					} else if("diffuse".equals(type)){
						res_mat.diffuse = setTextureOrColor(child, mesh, efx);
					} else if("specular".equals(type)){
						res_mat.specular = setTextureOrColor(child, mesh, efx);
					} else if("shininess".equals(type)){
						float[] vals = parse4floats(child.getChild("float").getContent(0).getValue());
						res_mat.intensity = vals[0];
					}
				}
			}
			mesh.setMaterial(res_mat);
		}
    }
    
    private void checkMaterials(Element mesh, CGMesh res_mesh) {
		if(mesh.getAttributeValue("material") != null){
			String mat = mesh.getAttributeValue("material");
			//mat = checkMaterialAlias(mesh, mat);
			getMaterial(mat, res_mesh); 
		}

    }

	public static void importMeshes(){
	    Element geometries = root.getChild("library_geometries");
	    if(geometries == null) return; // no meshes??
	    for(Object ob : geometries.getChildren()){
	    	Element geometry = (Element)ob;
	    	String name = geometry.getAttributeValue("id");
	    	Element mesh = geometry.getChild("mesh");
			IMPORTER.readMesh(mesh, name);
	    }
    }
	public static float[] parse4floats(String input){
		String[] flostr = input.split(" ");
		float[] floats = new float[flostr.length];
		for(int i = 0; i <flostr.length; i++)floats[i] = Float.parseFloat(flostr[i]);
		return floats;
	}
	public static int[] parse4ints(String input){
		String[] flostr = input.split(" ");
		int[] ints = new int[flostr.length];
		for(int i = 0; i <flostr.length; i++)ints[i] = Integer.parseInt(flostr[i]);
		return ints;
	}
	   public static final float DEG_TO_RAD = (float)Math.PI / 180.0f;

	public static boolean checkAndReadTransformation(Element tr, CGSceneNode dsnode){
		Matrix4f help = IMPORTER.factory.createMatrix();
		boolean isTrans = false;
		if(tr.getName().equals("rotate")){
			float[] vals = parse4floats(tr.getContent(0).getValue());
			assert(vals.length == 4);
			Vector3f vec = IMPORTER.factory.createVector();
			vec.set(vals[0],vals[1],vals[2]);
			help.setAngleAxis(vals[3]*DEG_TO_RAD, vec);
			dsnode.setLocalMatrix(help.mult(dsnode.getLocalMatrix()));
			isTrans = true;
		}else if(tr.getName().equals("translate")){
			help.loadIdentity();
			float[] vals = parse4floats(tr.getContent(0).getValue());
			help.setTranslation(vals[0],vals[1],vals[2]);
			dsnode.setLocalMatrix(dsnode.getLocalMatrix().mult(help));
			isTrans = true;
		}else if(tr.getName().equals("scale")){
			help.loadIdentity();
			float[] vals = parse4floats(tr.getContent(0).getValue());
			help.setScale(vals[0],vals[1],vals[2]);
			dsnode.setLocalMatrix(dsnode.getLocalMatrix().mult(help));
			isTrans = true;
		}else if(tr.getName().equals("matrix")){
			help.loadIdentity();
			float[] vals = parse4floats(tr.getContent(0).getValue());
			help.set(vals);
			//help.transpose();
			dsnode.setLocalMatrix(dsnode.getLocalMatrix().mult(help));
			isTrans = true;
		}
		return isTrans;
	}
	
	public static void addMesh(CGSceneNode dsnode, String meshname){
		dsnode.adddMesh(MESHES.get(meshname));
	}
	
	//TODO: This is not the RIGHT Controller loader, loads only the mesh associated with controller
	public static void loadController(CGSceneNode dsnode, String name, Element contrlNode){
		Element nodes = root.getChild("library_controllers");
		if(nodes == null) return;
		for(Object ob : nodes.getChildren()){
			Element node = (Element)ob;
			if(node.getName().equals("controller") && node.getAttributeValue("id").equals(name)){
				Element skin = node.getChild("skin");
				List<String> joints = new ArrayList<String>();
				List<Matrix4f> mats = new ArrayList<Matrix4f>();
				float[] weight = null, invM;
				Matrix4f bind = IMPORTER.factory.createMatrix();
				
				
				// read JOINTNames
				String sampJ="", sampW="", sampIM="";

				Element joint = skin.getChild("joints");
		  		for(Object o : joint.getChildren("input")){
	    			Element inp = (Element)o;
	    			String type = inp.getAttributeValue("semantic");
	    			if(type.equalsIgnoreCase("JOINT")){
	    				sampJ = inp.getAttributeValue("source");
	    				if(sampJ.startsWith("#"))sampJ = sampJ.substring(1);
	    			}else if(type.equalsIgnoreCase("INV_BIND_MATRIX")){
	    				sampIM = inp.getAttributeValue("source");
	    				if(sampIM.startsWith("#"))sampIM = sampIM.substring(1);
	    			}
	    		}
				
		  		Element weights = skin.getChild("vertex_weights");
		  		for(Object o : weights.getChildren("input")){
	    			Element inp = (Element)o;
	    			String type = inp.getAttributeValue("semantic");
	    			if(type.equalsIgnoreCase("JOINT")){
	    				sampJ = inp.getAttributeValue("source");
	    				if(sampJ.startsWith("#"))sampJ = sampJ.substring(1);
	    			}else if(type.equalsIgnoreCase("WEIGHT")){
	    				sampW = inp.getAttributeValue("source");
	    				if(sampW.startsWith("#"))sampW = sampW.substring(1);
	    			}
	    		}
		  		
		  		Element bindM = skin.getChild("bind_shape_matrix");
		  		float[] val = parse4floats(bindM.getContent(0).getValue());
		  		bind.set(val);
		  		
		  		//Now we have the jointlist name, weight name und inverse_MAtrix name
		  		for(Element inp : (List<Element>)skin.getChildren("source")){
	    			String type = inp.getAttributeValue("id");
	    			if(type.equalsIgnoreCase(sampJ)){
	    				//read the values for the jointNames
	       				Element names = inp.getChild("Name_array");
	       				if(names == null)names = inp.getChild("IDREF_array");
	    				String vals = names.getContent(0).getValue();
	    				String[] valstr =vals.split(" ");
	    				for(String s : valstr)joints.add(s);
	    			}else if(type.equalsIgnoreCase(sampW)){
	       				Element numbers = inp.getChild("float_array");
	    				int count = Integer.parseInt(numbers.getAttributeValue("count"));
			        	weight = IMPORTER.cache.getFloatArrays().get(numbers);
	    			}else if(type.equalsIgnoreCase(sampIM)){
	       				Element numbers = inp.getChild("float_array");
	    				int count = Integer.parseInt(numbers.getAttributeValue("count"))/16;
			        	invM = IMPORTER.cache.getFloatArrays().get(numbers);
	    				FloatBuffer fb = FloatBuffer.wrap(invM);
	    				fb.rewind();
	    				for(int i=0; i<invM.length/16;i++){
	    					Matrix4f mat =IMPORTER.factory.createMatrix();
	    					mat.readFloatBuffer(fb);
	    					mat.transposeLocal();
	    					mats.add(mat);
	    				}
	    			}
	    		}
		  		Element mulsE= weights.getChild("vcount");
		  		Element indsE= weights.getChild("v");
		  		int[] muls = parse4ints(mulsE.getContent(0).getValue());
		  		int[] inds = parse4ints(indsE.getContent(0).getValue());
		  		List<DSVertexWeights> wList = new ArrayList<DSVertexWeights>();
		  		int pos = 0;
		  		for(int i = 0 ; i< muls.length; i++){
		  			DSVertexWeights elem = new DSVertexWeights();
		  			float sum =0;
		  			for(int k = 0; k< muls[i]; k++){
		  				if(k<DSVertexWeights.MAXWEIGHTS){
		  					elem.jointIndex[k] = inds[pos++]; 
		  					elem.weight[k] = weight[inds[pos++]];
		  					sum += elem.weight[k];
		  				} else pos +=2; //skip this
		  			}
		  			if((sum != 1.0f) && (sum != 0.0f)){
		  				for(int z = 0; z<DSVertexWeights.MAXWEIGHTS;z++)elem.weight[z] /=sum;
		  			}
		  			wList.add(elem);
		  		}
		  		
		  		// now we hopefully found everything
		  		String meshname = skin.getAttributeValue("source");
				if(meshname.startsWith("#"))meshname = meshname.substring(1);
				//addMesh(dsnode,meshname);
		  		//Unfortunately we re-mixed the vertices when reading the mesh, clean that up now!
		  		List<DSVertexWeights> wListNew = new ArrayList<DSVertexWeights>();
		  		int[] mapping = MESH_IDXMAP.get(meshname);
		  		if(mapping != null){
		  			for(int i =0 ; i< mapping.length; i++){
		  				int idx = mapping[i];
		  				if(idx >= wList.size()){
		  					System.err.println("Mesh Controller Weight Index out of Range: " + idx + " Max: " +wList.size());
		  					idx = wList.size()-1;
		  				}
		  				wListNew.add(wList.get(idx));
		  			}
		  		}
		  		
		  		CGMesh orig = MESHES.get(meshname);
				Element mat = contrlNode.getChild("bind_material");
				if(mat != null){
					Element meshmat = IMPORTER.getChild(mat, new String[]{"technique_common","instance_material"});
					if(meshmat != null){
		    			String alis = meshmat.getAttribute("target").getValue();
		    			if(alis.startsWith("#")) alis = alis.substring(1);
						IMPORTER.getMaterial(alis,orig); 
					}
				}

				CGNodeSkinned ski = IMPORTER.factory.createSkinnedNode();
				
				ski.adddMesh(orig);
				orig.getVertices();
				ski.setInverseMatrices(mats);
				ski.setJoints(joints);
				ski.setWeights(wListNew);
				ski.setBindTransform(bind);
				dsnode.addChildNode(ski);
				
			}
		}
	}
	

	public static CGSceneNode parseNode(Element root, CGScene scene){
		Matrix4f help = IMPORTER.factory.createMatrix();
		String id = root.getAttributeValue("name");
		if(id == null)
		{
			id = root.getAttributeValue("id");
		}

		CGSceneNode dsnode = AnimatedNodeNames.contains(id) ? IMPORTER.factory.createAnimationNode(): IMPORTER.factory.createSceneNode();
		if ("JOINT".equalsIgnoreCase(root.getAttributeValue("type"))){
			// root is a JOINT
		}
		
		if(id != null)dsnode.setName(id);

		String sid = root.getAttributeValue("sid");
		if(sid != null){
			dsnode.setShortName(sid);
		}
		
		for(Object ob : root.getChildren()){
			Element node = (Element)ob;
			if(node.getName().equals("instance_camera")){
				//root is a Camera
				Vector3f vec = ColladaImporter.factory().createVector();
				dsnode.getLocalMatrix().toTranslationVector(vec);
				//System.out.println("TransVec: " + vec.getX() + "|" + vec.getY() + "|" + vec.getZ());
				scene.setCamPos(vec);
				Vector3f look = IMPORTER.factory.createVector();
				look.set(0,0,1);
				look = dsnode.getLocalMatrix().mult(look);
				look = look.mult(-1);
				scene.setCamDir(look);
			} else if (node.getName().equals("instance_light")){
				//root is a Light
				String ctrlname = node.getAttributeValue("url");
				if(ctrlname.startsWith("#"))ctrlname = ctrlname.substring(1);
				float[] params = IMPORTER.getLightParams(ctrlname);
				Vector3f col = IMPORTER.factory.createVector();
				col.set(params[1], params[2], params[3]);
				Vector3f vec = ColladaImporter.factory().createVector();
				dsnode.getLocalMatrix().toTranslationVector(vec);
				scene.addLight(vec, col, params[0]); 
			}else  if (node.getName().equals("instance_controller")){
				// root is controller --> Handle like MESH for NOW
				String ctrlname = node.getAttributeValue("url");
				if(ctrlname.startsWith("#"))ctrlname = ctrlname.substring(1);
				loadController(dsnode,ctrlname, node);
			}else  if (node.getName().equals("instance_geometry")){
				// root has a mesh --> load mesh
				String meshname = node.getAttributeValue("url");
				if(meshname.startsWith("#"))meshname = meshname.substring(1);
		  		CGMesh orig = MESHES.get(meshname);
				Element mat = node.getChild("bind_material");
				if(mat != null){
					Element meshmat = IMPORTER.getChild(mat, new String[]{"technique_common","instance_material"});
					if(meshmat != null){
		    			String alis = meshmat.getAttribute("target").getValue();
		    			if(alis.startsWith("#")) alis = alis.substring(1);
						IMPORTER.getMaterial(alis,orig); 
					}
				}

				dsnode.adddMesh(orig);
			}else  if (node.getName().equals("node")){
				// root has a subnode --> load node
				dsnode.addChildNode(parseNode(node, scene));
			}else if(!checkAndReadTransformation(node, dsnode)){
				//DO NOT KNOW what that is...
			}
		}
		return dsnode;
	}
	
	
	public static CGScene add2Scene(CGScene scene, String filename){
		return add2Scene(scene, filename, false);
	}
	public static CGScene add2Scene(CGScene scene, String filename, boolean animationsonly){
		CGObjectFactory fac = scene.getObjectFactory();
		File testfile = new File(filename);
    	InputStream in;
		try {
			in = new FileInputStream(testfile);
			modelpath = testfile.getParent()+"\\";
	    	root = IMPORTER.readCollada(in);
	    	XMLOutputter output = new XMLOutputter(Format.getPrettyFormat());
	        //output.output(root, System.out);
	
	    	if(!animationsonly){
		    	importMeshes();
				Element nodes = root.getChild("library_animations");
				IMPORTER.readAnimatedNodeNames(nodes);
				
		    	//for now: Load FIRST Scene found!!
		        Element sceneE = root.getChild("library_visual_scenes").getChild("visual_scene");
		        
				CGSceneNode dsnode = IMPORTER.factory.createSceneNode();
				scene.getRoot().setParent(dsnode);
		        scene.getRoot().addChildNode(parseNode(sceneE, scene));
	    	}
	        
	        IMPORTER.readAnimations(root.getChild("library_animations"), scene);
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
    	
		
		return scene;
	}
	
	public static CGScene readScene(String filename, CGObjectFactory fac){
		IMPORTER.factory = fac;
		CGScene scene = IMPORTER.factory.createScene();
		scene.setObjectFactory(fac);
		return add2Scene(scene, filename);
	}
    
    public static void main(String[] args) throws IOException{
    	//CGScene sc = readScene("./data/head.dae", new CGObjectFactoryImpl());
    	//ColladaImporter.importMeshes("./data/cube.dae", meshes);
    }
}
