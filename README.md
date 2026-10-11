I was watching YouTube while eating and came across this video by 3Blue1Brown about how M.C. Escher used complex analysis to make his piece "Print Gallery".
I thought it was really cool how such complex math topics were used to make art and so I wanted to make a piece that employed math as well. I learned a bit from
another video I ran into a week later by TheGrayCuber on YouTube about applying complex transformations to the globe by using a Riemann sphere which gave me an idea
on how I'd want to approach making a piece like Escher. Thus began the development of this program.

Essentially what it does is:
 - Get a pixel
 - Convert the pixel to a point on an XY plane
 - Use stereographic projection to map it onto unit sphere (the Riemann sphere, which is a compact representation of the complex plane)
 - Convert those coordinates to the complex plane, creating a complex number using the custom ComplexNumber class
 - Apply the desired transformations (I have it set to a square and multiply function but there are other functions I added in the ComplexNumber class that can also be used)
 - Project it back into the XY plane, modulo, and find which pixels in the original image roughly correlate with that (inverse mapping)
 - Use bilinear interpolation so that pixels with decimal values fit in the image
 - Repeat for every pixel and output

What I learned from making this program was the workflow that shaders and image processors use and more importantly the little bits of math that let a computer make pictures
which I found really cool. Alongside that, I also learned a lot about complex numbers past the basic introduction I had in school which was really fun as well.

Before showcasing some of the output transformations, here is the original image:

![Original Image](<./Example Images/original.png>)
