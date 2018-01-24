# Android Architectural Templates

<h2>MVI (Model-View-Intent)</h2>
<P>(<B>View</B>(<I>Model</I>(<STRONG>Intent</STRONG>))</P>

<P><B>View</B> - it's as usual simple. View reflects what user see, i.e. UI.</P>
<P><B>Intent</B> - it provides intention (like user clicks, scrolls, etc.) from <I>View</I> to <I>Model</I>. In a nutshell this entity like a binder that binds Model and View.</P>
<P><B>Model</B> - it receives an input provided by <I>Intent</I> and after processing data (do business logic) create a new ModelState (immutability) and provides it to <I>Intent</I> (which in its turn render this state in <I>View</I>).</P>
<P>Flow:<P>
<P><B>View</B> <i>-UI events-></i> <STRONG>Intent</STRONG> -action to manipulate model-> <B>Model</B> -new model to display-> <B>View</B></P>
<UL>
  <LI><H3>Simple list implementation</H3>
    <P>
      https://github.com/appspell/Android-Architectural-Templates/tree/master/app/src/main/java/com/appspell/android/templates/mvi/simplelist
    </P>
  </LI>
  <LI>
    <H3>Base implementation of list</H3>
    <P>
      https://github.com/appspell/Android-Architectural-Templates/tree/master/app/src/main/java/com/appspell/android/templates/mvi/list
    </P>
  </LI>
</UL>
