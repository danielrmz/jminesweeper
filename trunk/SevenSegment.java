import java.awt.*;


/** Seven Segment Led Class
 @author Simon Horman
 * 
 * <p>(c) 1996 Verge Systems International<br>
 * The SevenSegment Led Class was consieved, designed and implimented by
 * Simon Horman of Verge Systems International and remains the property
 * of Laura Morris.
 * </p>
 * 
 * <p>Permision is herby granted to freely use this class for any purpose as 
 * long a the author is acknowladged.
 * </p>
 * 
 * <b>Features</b>
 * <ul>
 *        <li> Hexadecimal digits 0-F are directly supported. 
 *        <li> Individual Segments can be made active and inactive. 
 *        <li> Can be created in any size. 
 *        <li>  Backround, Led Avtive and Led Inactive coulours can be changed at any time. 
 *        <li> Entire seven segment display can be turned on and off, activating and deactivationg the displaying
 *        <li> of inactive as well as actice leds. 
 * </ul>
 * 
 * <b>The Segments</b><br>
 * <pre>
 *      A    
 *    ----   
 *  F|    |B 
 *   |  G |  
 *    ----   
 *  E|    |C 
 *   |    |  
 *    ----   
 *      D    
 * </pre>
 */

public class SevenSegment extends Canvas{
   /* The currnet hex value displayed on the Display 
    * 0x10 if segmants are being indivitually manip[ulated. */
   private int value;
   // The Image we draw on
   private Image image;
   // Our Dimensions
   private Dimension size;
   /* The Polygons dor the segments.
    * We need only render these once */
   private Polygon pA, p[];
   /* The state of each segment
    * 0 inactive, 1 active */
   private int segment_state[];
   // The Background Colour
   private Color background_colour;
   // The colour of active segments.
   private Color foreground_colour;
   // The colour of inactive segments.
   private Color inactive_colour;
   // Is the whole display on
   boolean ison;
   // The Graphics 
   Graphics g;
   
   /** Create a Seven Segment display with all segments inactive.
    @param parent The parent component usually an applet
    @param width the width for the Seven Segment
    @param height the height for the Seven Segment
    */
   public SevenSegment(Component parent, int width, int height){
      this(parent, width, height, 0x10);
   }
   
   /** Create a Seven Segment display displaying a hexadecimal value.
    @param parent The parent component usually an applet
    @param width the width for the Seven Segment
    @param height the height for the Seven Segment
    @param value the hexadecimnal value form 0 to F to display
    */
   public SevenSegment(Component parent, int width, int height, int value){
      background_colour = Color.black;
      foreground_colour = Color.red;
      inactive_colour =new Color(0x400000);
      
      ison = true;
      
      size = new Dimension(width, height);
      image = parent.createImage(size.width, size.height);
      g = image.getGraphics();

      createSegments();
      segment_state = new int[7];
      setValue(value);
   }
   
   /** Change the background color. Redraws interbally the Seven Segment
    * Display but does not paint it onto the parent compotents graphic.
    @param background the new background colour
    */
   public void setBackground(Color background){
      background_colour = background;
      draw();
   }
   
   /** Change the foreground color. This is the colour that active segments 
    * are painted in Redraws interbally the Seven Segment Display but does 
    * not paint it onto the parent compotents graphic.
    @param foreground the new foreground colour
    */
   public void setForeground(Color foreground){
      foreground_colour = foreground;
      draw();
   }

   /** Change the inactive color. Redraws internally the Seven Segment
    * Display but does not paint it onto the parent compotents graphic.
    @param background the new background colour
    */
   public void setInactiveColour(Color inactive){
      inactive_colour = inactive;
      draw();
   }

   /** Set a segment to be active or inactive.
    @param segment the segment to set 0 corresponds to segment A 
    through to  7 corresponding to segment G.
    @param value State to set the segment to. 0 inactive 1 active.
    */
   public void setSegment(int segment, int value){
      this.value = 0x10;
      segment_state[segment] = value;
   }
   
   /** Set a segment to be active or inactive.
    @param segment the segment. A through G are valid.
    @param value State to set the segment to. 0 inactive 1 active.
    */
   public void setSegment(char segment, int value){
      setSegment((int)(segment-'@'), value);
   }
   
   /** Set the Hexadecimal digit displayed by the Seven Segment.
    @param value The Hexcadegimal digit to display. Valued form 0 to 0xF
    are valid.
    */
   public void setValue(int value){
      int a, b, c, d;
      int na, nb, nc, nd;
      
      if((this.value = value)<0x10){
	 a = value & 1;
	 b = (value & 2) >>> 1;
	 c = (value & 4) >>> 2;
	 d = (value & 8) >>> 3;
	 
	 na = ~a & 1;
	 nb = ~b & 1;
	 nc = ~c & 1;
	 nd = ~d & 1;

	 //A
	 segment_state[0] = (na&nc)|(na&d)|(b&nd)|(nb&nc&d)|(a&c&nd)|(b&c&d);
	 //B
	 segment_state[1] = (nc&nd)|(na&nc)|(na&nb&nd)|(a&nb&d)|(a&b&nd);
	 //C
	 segment_state[2] = (nb&nd)|(a&nd)|(c&nd)|(nc&d)|(a&nb);
	 //D
	 segment_state[3] = (na&nc&nd)|(a&b&nc)|(a&nb&c)|(na&nb&d)|(na&b&c);
	 //E
	 segment_state[4] = (na&b)|(c&d)|(b&d)|(na&nc);
	 //F
	 segment_state[5] = (na&nb)|(nc&d)|(b&d)|(nb&c&nd)|(na&c&nd);
	 //G
	 segment_state[6] = (na&b)|(nc&d)|(nb&c&nd)|(b&nc&nd)|(a&c&d);
      }
   }

   /** Get the colour of inactive leds 
    */
   public Color getInactiveColour(){
      return inactive_colour;
   }

   /** Get the foreground colour. This is the colour used for 
    * active leds
    */
   public Color getForeground(){
      return foreground_colour;
   }

   /** Get the background Colour
    */
   public Color getBackground(){
      return background_colour;
   }

   /** Get the state of a segment. 
    @param segment the segment to set 0 corresponds to segment A
    through to  7 corresponding to segment G.
    @return 0 corresponds to active and 1 to inactive.
    */
   public int getSegment(int segment){
      return segment_state[segment];
   }
   
   /** Get the state of a segment. 
    @param segment the segment. Valid segments are A to G.
    @return 0 corresponds to active and 1 to inactive.
    */
   public int getSegment(char segment){
      return getSegment((int)(segment-'@'));
   }
   
   /** Get the hexadecimal value currently displayed by the Seven Segment.
    * A return value of 0x10 signifies that segments are beign manipulated.
    */
   public int getValue(){
      return value;
   }
   
   /** An ascii aoutput of the Seven Segment
    */
   public String toString(){
      String string;
      
      string  = " "              + segment_state[0] 
	                         + segment_state[0]                    + "\n";
      string += segment_state[5] + "  "             + segment_state[1] + "\n";
      string += segment_state[5] + "  "             + segment_state[1] + "\n";
      string +=" "               + segment_state[6] 
	                         + segment_state[6]                    + "\n";
      string += segment_state[4] + "  "             + segment_state[2] + "\n";
      string += segment_state[4] + "  "             + segment_state[2] + "\n";
      string +=" "               + segment_state[3]
	                         + segment_state[3]                    + "\n";
      
      return string;
   }

   // Render the segments
   private void createSegments(){
      p = new Polygon[7];
      
      int gap = (int)(0.01*Math.min(size.width, size.height));
      if(gap==0){
	 gap = 1;
      }
      
      //A
      p[0] = new Polygon();
      p[0].addPoint((int)(0.05*size.width),(int)(0.05*size.height)-gap); 
      p[0].addPoint((int)(0.95*size.width),(int)(0.05*size.height)-gap); 
      p[0].addPoint((int)(0.85*size.width),(int)(0.15*size.height)-gap); 
      p[0].addPoint((int)(0.15*size.width),(int)(0.15*size.height)-gap); 
      p[0].addPoint((int)(0.05*size.width),(int)(0.05*size.height)-gap); 

      //B
      p[1] = new Polygon();
      p[1].addPoint((int)(0.95*size.width)+gap,(int)(0.05*size.height)); 
      p[1].addPoint((int)(0.95*size.width)+gap,(int)(0.5*size.height)); 
      p[1].addPoint((int)(0.85*size.width)+gap,(int)(0.45*size.height)); 
      p[1].addPoint((int)(0.85*size.width)+gap,(int)(0.15*size.height)); 
      p[1].addPoint((int)(0.95*size.width)+gap,(int)(0.05*size.height)); 

      //C
      p[2] = new Polygon();
      p[2].addPoint((int)(0.95*size.width)+gap,(int)(0.5*size.height)); 
      p[2].addPoint((int)(0.95*size.width)+gap,(int)(0.95*size.height)); 
      p[2].addPoint((int)(0.85*size.width)+gap,(int)(0.85*size.height)); 
      p[2].addPoint((int)(0.85*size.width)+gap,(int)(0.55*size.height)); 
      p[2].addPoint((int)(0.95*size.width)+gap,(int)(0.5*size.height)); 

      //D
      p[3] = new Polygon();
      p[3].addPoint((int)(0.05*size.width),(int)(0.95*size.height)+gap); 
      p[3].addPoint((int)(0.95*size.width),(int)(0.95*size.height)+gap); 
      p[3].addPoint((int)(0.85*size.width),(int)(0.85*size.height)+gap); 
      p[3].addPoint((int)(0.15*size.width),(int)(0.85*size.height)+gap); 
      p[3].addPoint((int)(0.05*size.width),(int)(0.95*size.height)+gap); 

      //E
      p[4] = new Polygon();
      p[4].addPoint((int)(0.05*size.width)-gap,(int)(0.5*size.height)); 
      p[4].addPoint((int)(0.05*size.width)-gap,(int)(0.95*size.height)); 
      p[4].addPoint((int)(0.15*size.width)-gap,(int)(0.85*size.height)); 
      p[4].addPoint((int)(0.15*size.width)-gap,(int)(0.55*size.height)); 
      p[4].addPoint((int)(0.05*size.width)-gap,(int)(0.5*size.height)); 
      
      //F
      p[5] = new Polygon();
      p[5].addPoint((int)(0.05*size.width)-gap,(int)(0.05*size.height)); 
      p[5].addPoint((int)(0.05*size.width)-gap,(int)(0.5*size.height)); 
      p[5].addPoint((int)(0.15*size.width)-gap,(int)(0.45*size.height)); 
      p[5].addPoint((int)(0.15*size.width)-gap,(int)(0.15*size.height)); 
      p[5].addPoint((int)(0.05*size.width)-gap,(int)(0.05*size.height)); 

      //G
      p[6] = new Polygon();
      p[6].addPoint((int)(0.05*size.width)+gap,(int)(0.5*size.height)); 
      p[6].addPoint((int)(0.15*size.width)    ,(int)(0.45*size.height)+gap); 
      p[6].addPoint((int)(0.85*size.width)    ,(int)(0.45*size.height)+gap); 
      p[6].addPoint((int)(0.95*size.width)-gap,(int)(0.5*size.height)); 
      p[6].addPoint((int)(0.85*size.width)    ,(int)(0.55*size.height)-gap); 
      p[6].addPoint((int)(0.15*size.width)    ,(int)(0.55*size.height)-gap); 
      p[6].addPoint((int)(0.05*size.width)+gap,(int)(0.5*size.height)); 

   }

   /** Draw the Seven Segment internally
    */
   public void draw(){   
      g.setColor(background_colour);
      g.fillRect(0, 0, size.width, size.height);

      if(ison){
	 g.setColor(foreground_colour);
	 for(int i=0; i<7;i++){
	    if(segment_state[i]==1){
	       g.fillPolygon(p[i]);
	    }
	 }
	 g.setColor(inactive_colour);
	 for(int i=0; i<7;i++){
	    if(segment_state[i]==0){
	       g.fillPolygon(p[i]);
	    }
	 }
      }
   }
   
   /** Paint the Seven Segment
    @param graphics the graphics to paint on
    */
   public void paint(Graphics g){
      try{g.drawImage(image, 0, 0, this);}
      catch(NullPointerException e){}
   }
   
   /** Preferred Size for Layout
    */
   public Dimension preferredSize(){
      return size;
   }
   
   /** Minimum size for layout
    */
   public Dimension minimumSize(){
      return size;
   }
   
   /** Turn the Seven Segment on.
    * When on active and inactive segments are displayed as expected.
    * This is the default state for the Seven Segment.
    */
   public void turnOn(){
      ison = true;
      draw();
   }
   
   /** Turn off Seven Segment.
    * When off the Seven Segment is dawn as a rectangle in its background
    * colour.
    */
   public void turnOff(){
      ison = false;
      draw();
   }
   
   /** Is the Seven Segment on or off
    @return true if Seven Segment is on.
    */
   public boolean isOn(){
      return ison;
   }
}



