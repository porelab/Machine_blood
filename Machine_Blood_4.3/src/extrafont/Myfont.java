package extrafont;

//for setting software fonts

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class Myfont{
	
	Font ms,rr,mm,bn;
	
	public Myfont(int size)
	{
	ms=	Font.font(Myfont.class.getResource("Montserrat-SemiBold.ttf").toExternalForm());
	rr=	Font.loadFont(Myfont.class.getResource("Roboto-Regular.ttf").toExternalForm(), size);
	mm=	Font.loadFont(Myfont.class.getResource("Montserrat-Medium.ttf").toExternalForm(), size);
	bn=	Font.loadFont(Myfont.class.getResource("BebasNeue.otf").toExternalForm(), size);
	}
	
	public Font getM_S_B()
	{
		
		return ms;
	}
	
	public Font getR_R()
	{
		return rr;
	}
	
	public Font getM_M()
	{
		return mm;
	}
	
	public Font getB_N()
	{
		return bn;
	}

}
