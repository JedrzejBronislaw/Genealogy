package treeGraphs.painter;

import java.lang.reflect.InvocationTargetException;

import treeGraphs.painter.service.PainterService;
import treeGraphs.painter.service.Graphics2DPainterService;
import treeGraphs.painter.service.FXPainterService;

public enum PainterServiceType {
	FX(FXPainterService.class),
	Graphics2D(Graphics2DPainterService.class);
	
	private Class<? extends PainterService> nameClass;
	
	private PainterServiceType(Class<? extends PainterService> nameClass) {
		this.nameClass = nameClass;
	}
	
	public PainterService createPainterService() {
		try {
			return (PainterService) nameClass.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| SecurityException | NoSuchMethodException e) {

			e.printStackTrace();
			return null;
		}
	}

	boolean ifMatch(Class<? extends PainterService> nameClass) {
		return this.nameClass == nameClass;
	}

	public static PainterServiceType get(Class<? extends PainterService> nameClass) {
		PainterServiceType[] values = values();
		
		for (PainterServiceType type : values)
			if (type.ifMatch(nameClass)) return type;
		
		return null;
	}
}
