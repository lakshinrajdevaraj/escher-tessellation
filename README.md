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

When doing these complex transformations, there are 2 main points of interest to consider, 0 and infinity. Since the program uses inverse mapping, the output pixels are transformed
and then look to the original image for what color to become. As output pixels get smaller and smaller towards zero, they get closer to the top left corner of the original image.
On the other hand numbers that get higher and higher towards infinity will end up wrapping through the whole image thanks to the modulo function my code. What this does is repeat
the image continually as numbers get higher which gives the repeating tessellation effect.

As for the output image, it's normalized and then centered, which makes the center of the output image (0,0), the top left (-x, y), the top right (x, y), the bottom left (-x, -y), and the bottom right (x, y).
Being aware of the major points on the output and input image helps a lot when visualizing what the transformations are actually doing.

Now onto the transformations themselves!

![Original Image](<./Example Images/squaredivided.png>)

This transformation here is the image squared and then multiplied by 0.4 (divided by 2.5). Squaring something makes it so negatives and positives yield the same result. As such, this image duplicates
itself twice around 0. This image is also divided by 2.5, which pushes all the numbers towards 0 (the center on the output image) and makes them loop less because they don't accelerate towards infinity
as fast. This makes a kind of zoom in effect for this transformation specifically. Conversely, multiplying it would accelerate it towards infinity which would push everything away from the center and
loop aggressively. Less math related but out of all the transformations I've tried out, this is the one I found the most aesthetically pleasing.




![Original Image](<./Example Images/reciprocal.png>)

This transformation divides 1 by the image. What this means is that as the number gets smaller (towards 0), it starts rapidly accelerating to infinity. On the other hand, the greater the denominator is,
the closer it approaches 0 on the input which is the black corner. You might notice the grey on the right side of the picture, this is because of the grey highlight on my origin. It's incredible minor but
it is important to mention, the very center point is (0,0) which means that when taking the reciprocal the program divides by 0. This returns NaN, which is carried throughout the whole transformation process
afterwards. When rounded at the end, NaN becomes 0 which just maps to the corner of the original image. However, because of the bilinear interpolation, the RGB value becomes 0 because of NaN affecting the
weight of the interpolation. This becomes black, meaning that whenever NaN is returned in the program it will always become black if bilinear is turned on. With nearest neighbor, it becomes the color of the
very top left pixel (which with this image is black anyways). In this case, only the very center pixel is affected by this which when you zoom in you can see is black.




![Original Image](<./Example Images/exponent.png>)

This transformation takes e to the power of the image. The left side here has a negative x, and e taken to a negative power increasingly approaches 0. This is why the left side is completely dominated by
black pixels. On the other hand, the right side is positive which when set as the power of e makes it rapidly accelerate towards infinity and thus constantly loop. A big thing to notice here are the weird
waves happening vertically. This is because the formula for taking e to the power of a complex number involves multiplying with a cosine and sine function which creates the oscillating effect you see.
