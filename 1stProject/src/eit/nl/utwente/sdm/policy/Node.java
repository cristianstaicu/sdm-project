package eit.nl.utwente.sdm.policy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class Node {

	public static final int INDENT_LAST_ROW = 15; 
	
	public final Node childLeft;
	public final Node childRight;

	private Node parent;
	
	public Node(Node childLeft, Node childRight) {
		this.childLeft = childLeft;
		this.childRight = childRight;
		if (childLeft != null) {
			childLeft.setParent(this);
		}
		if (childRight != null) {
			childRight.setParent(this);
		}
	}
	
	private void setParent(Node parent) {
		this.parent = parent;		
	}

	@Override
	public String toString() {
		Map<Integer, List<Node>> levels = new HashMap<Integer, List<Node>>();
		int noLevels = getNoLevels();
		setTreeByLevels(levels, 0, noLevels);
		int maxElsPerLevel = 0;
		for (Integer level : levels.keySet()) {
			if (levels.get(level).size() > maxElsPerLevel)
				maxElsPerLevel = levels.get(level).size();
		}
		int width = maxElsPerLevel * INDENT_LAST_ROW;
		Map<Node, Integer> indents = new HashMap<Node, Integer>();
		String result = "";
		for (Integer level : levels.keySet()) {
			int cursorPosition = 0;
			List<Node> currentLevel = levels.get(level);
			int distance = width / (currentLevel.size() + 1);
			for (Node el : currentLevel) {
				int parentIndent;
//				System.out.println(el.parent);
				if (el.parent == null) {
					parentIndent = width - distance / 2;
				} else { 
					parentIndent = indents.get(el.parent);
				}
				int noSpaces;
				if (el.parent != null && el.equals(el.parent.childRight))
					noSpaces = parentIndent - cursorPosition + distance / 2;
				else 
					noSpaces = parentIndent - cursorPosition - distance / 2;
				for (int i = 0; i < noSpaces; i++) {
					result += " ";
					cursorPosition ++;
				}
				indents.put(el, cursorPosition);
				result += el.getLabel();
				cursorPosition += el.getLabel().length();
			}
			result += "\n";
		}
		return result;
	}

	private int getNoLevels() {
		int noLevelsLeft = 0, noLevelsRight = 0;
		if (childLeft != null) {
			noLevelsLeft = childLeft.getNoLevels();
		}
		if (childRight != null) {
			noLevelsRight = childRight.getNoLevels();
		}
		if (noLevelsLeft > noLevelsRight) {
			return noLevelsLeft + 1;
		} else {
			return noLevelsRight + 1;
		}
	}

	private void setTreeByLevels(Map<Integer, List<Node>> levels,
			int currentLevel, int noLevels) {
		if  (levels.containsKey(currentLevel)) {
			levels.get(currentLevel).add(this);
		} else {
			List<Node> currentLevelList = new ArrayList<Node>();
			currentLevelList.add(this);
			levels.put(currentLevel, currentLevelList);
		}
		if (childLeft != null) {
			childLeft.setTreeByLevels(levels, currentLevel + 1, noLevels);
		}
		if (childRight != null) {
			childRight.setTreeByLevels(levels, currentLevel + 1, noLevels);
		}
		
	}

	abstract public String getLabel();

	public Node getParent() {
		return parent;
	}
	
	public Set<String> getMinimalAttrSet(List<String> attributes) {
		Set<HashSet<String>> attrs = getAttrsAsSets();
		System.out.println(attrs);
		HashSet<String> minimal = null;
		for (HashSet<String> requiredAttrs : attrs) {
			boolean hasAllAttrs = true;
			for (String currentAttr : requiredAttrs) {
				if (!attributes.contains(currentAttr)) {
					hasAllAttrs = false;
				}
			}
			if (hasAllAttrs && (minimal == null || minimal.size() > requiredAttrs.size())) {
				minimal = requiredAttrs;
			}
		}
		return minimal;
	}

	/**
	 * This method assumes all the leafs are of type AttributeNode
	 */
	private Set<HashSet<String>> getAttrsAsSets() {
		Set<HashSet<String>> result = new HashSet<HashSet<String>>();
		if (this instanceof AttributeNode) {
			HashSet<String> nodeSet = new HashSet<String>();
			nodeSet.add(getLabel());
			result.add(nodeSet);
		} else if (this instanceof OrNode) {
			Set<HashSet<String>> requiredLeft = childLeft.getAttrsAsSets();
			Set<HashSet<String>> requiredRight = childRight.getAttrsAsSets();
			for (HashSet<String> set : requiredLeft) {
				result.add(set);
			}
			for (HashSet<String> set : requiredRight) {
				result.add(set);
			}
		} else if (this instanceof AndNode) {
			Set<HashSet<String>> requiredLeft = childLeft.getAttrsAsSets();
			Set<HashSet<String>> requiredRight = childRight.getAttrsAsSets();
			for (HashSet<String> setLeft : requiredLeft) {
				for (HashSet<String> setRight : requiredRight) {
					HashSet<String> combinedSet = new HashSet<String>();
					combinedSet.addAll(setLeft);
					combinedSet.addAll(setRight);
					result.add(combinedSet);
				}
			}
		}
		return result;
	}
	
}
