package chess;

import java.util.LinkedList;

import platform.utils.SerializableReflectObject;

public class RecordedGame extends SerializableReflectObject {
	public LinkedList<Move> whitesMoves, blacksMoves;
}
