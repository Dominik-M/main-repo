package main;

/**
 *
 * @author Dominik Messerschmidt
 */
public class Node
{

    String text;
    Node[] children;

    public Node(String text, Node... children)
    {
        this.text = text;
        this.children = children;
    }

    public boolean isEnd()
    {
        return children == null || children.length < 1;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public Node[] getChildren()
    {
        return children;
    }

    public void setChildren(Node[] children)
    {
        this.children = children;
    }

}
