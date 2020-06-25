package main;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import model.Tree;
import model.pgl.virtual.PGLDiffReport;

@AllArgsConstructor
@Getter
public class TreeAndReport {

	private Tree tree;
	@NonNull
	private PGLDiffReport report;
}
