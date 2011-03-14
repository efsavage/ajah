/**
Ajah HTML library, for consuming and creating HTML markup.

<p>"Wait, what?  Create HTML with Java?  Isn't that a terrible idea?", you ask, 
suddenly doubting my abilities as a developer.  "Is this guy some crackpot, 
some ... noob?  I bet he even loved EJB1s.  Forget this loser and all of the 
code he's written."</p>

<p>So let's clarify.  First, I never liked EJBs.  Second, it's not a great idea.  
I'd love to do this some more elegant way.  I'd love to even use someone else's 
not-great idea to do this so I can spend more time on less-not-great ideas.  The 
fact that I have to justify a package's existence in a package-info.java is a big 
clue that this is not something to be proud of, and yet I'm not only doing it, 
I'm putting it out in public.  Why?</p>

<ol>
<li>It's less bad than other options - I need to write HTML automatically.  I 
could have done this with velocity templates or string bundles.  I could have 
embedded a JSP engine and written .tag files.  I won't say those are necessarily 
worse ideas, but I don't see them being much better either.</li>
<li>ECS is dead - Apache/Jakarta had a project that did basically the same, but 
it was abandoned.  It was old code that didn't use nice new Java features, so 
rather than use it I just started clean.</li>
<li>JSF is terrible - Seriously, it's a disaster.  I'd rather use EJBs.  OK, 
maybe not that bad, but it's bad.</li>
<li>I'd like to find a better way - This addresses the "why is this public" 
question more than the "why did you do this" quetsion.  So please, suggest 
away.</li>
</ol>


@author Eric F. Savage
@version 1.0
 */
package com.ajah.html;