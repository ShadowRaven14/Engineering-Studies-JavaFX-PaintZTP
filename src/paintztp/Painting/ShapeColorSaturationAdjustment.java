package paintztp.Painting;

//więcej komentarzy poproszę
public class ShapeColorSaturationAdjustment extends ShapeColorAdjustment 
{
    public void execute(Color col, int value)
    {
        float r=col.r;
        float g=col.g;
        float b=col.b;
        float R=r/255;
        float G=g/255;
        float B=b/255;
        float max = (r > g && r > b) ? r : (g > b) ? g : b;
        float min = (r < g && r < b) ? r : (g < b) ? g : b;
        float h, s, l;
        l = (max + min) / 2.0f;

        if (max == min) 
        {
            h = s = 0.0f;
        } 
        else 
        {
            float d = max - min;
            s = (l > 0.5f) ? d / (2.0f - max - min) : d / (max + min);

            if (r > g && r > b)
                h = (g - b) / d + (g < b ? 6.0f : 0.0f);

            else if (g > b)
                h = (b - r) / d + 2.0f;

            else
                h = (r - g) / d + 4.0f;

            h /= 6.0f;
        }
        
        s=value;
        
            if(s==0)
            {
                r=g=b=l;
            }
            else 
            {
                float q = l < 0.5f ? l * (1 + s) : l + s - l * s;
                float p = 2 * l - q;
                r = hueToRgb(p, q, h + 1f/3f);
                g = hueToRgb(p, q, h);
                b = hueToRgb(p, q, h - 1f/3f);
            }
            
            col.r=(int)Math.min(255, 256*r);
            col.g=(int)Math.min(255, 256*g);
            col.b=(int)Math.min(255, 256*b);
        };
    
    public static float hueToRgb(float p, float q, float t) 
    {
        if (t < 0f)
            t += 1f;
        if (t > 1f)
            t -= 1f;
        if (t < 1f/6f)
            return p + (q - p) * 6f * t;
        if (t < 1f/2f)
            return q;
        if (t < 2f/3f)
            return p + (q - p) * (2f/3f - t) * 6f;
        return p;
    }
}