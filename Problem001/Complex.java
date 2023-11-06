
public class Complex 
{
	private double real;
	private double imag;
	 
	public Complex(double real, double imag) 
	{
		this.real = real;
		this.imag = imag;
	}

	public double getReal() 
	{
		return real;
	}

	public double getImag() 
	{
		return imag;
	}
	
	public Complex add(Complex z) 
	{
        return new Complex(this.real + z.getReal(), this.imag + z.getImag());
    }
	
	public Complex multiply(Complex z) 
	{
        double realPart = this.real * z.getReal() - this.imag* z.getImag();
        double imagPart = this.real * z.getImag() + this.imag * z.getReal();
        return new Complex(realPart, imagPart);
    }

    public double abs() 
    {
        return Math.sqrt(real * real + imag * imag);
    }
	


}
