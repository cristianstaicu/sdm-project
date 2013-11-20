package eit.nl.utwente.sdm.policy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
}
