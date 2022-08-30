// Program to generate exponentially distributed random numbers given a seed and an arrival rate
import java.util.Random;

public class ExpoRandom
{
  private Random r;
  private long seed;
  private double lambda;
  
  public ExpoRandom( long s, double l )
  {
    seed = s;
    lambda = l;
    r = new Random(seed);
  }
  public double nextValue()
  {
    double rvalue = r.nextDouble();
    return -1*(1./lambda)*(Math.log( 1 - rvalue ) );
  }
}