package com.rat6.chessonline.framework;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Font {
    public final Texture texture;
    public final int glyphWidth;
    public final int glyphHeight;
    public final TextureRegion[] glyphs = new TextureRegion[96];

    public Font(Texture texture, int offsetX, int offsetY, int glyphsPerRow, int glyphWidth, int glyphHeight) {
        this.texture = texture;
        this.glyphWidth = glyphWidth;
        this.glyphHeight = glyphHeight;
        int x = offsetX;
        int y = offsetY;
        for(int i = 0; i < 96; i++) {
            glyphs[i] = new TextureRegion(texture, x, y, glyphWidth, glyphHeight);
            x += glyphWidth;
            if(x == offsetX + glyphsPerRow * glyphWidth) {
                x = offsetX;
                y += glyphHeight;
            }
        }
    }

    public void drawText(SpriteBatch batch, String text, float x, float y, float letterWidth, float letterHeight) {
        int len = text.length();
        for(int i = 0; i < len; i++) {
            int c = text.charAt(i) - ' ';
            if(c < 0 || c > glyphs.length - 1)
                continue;
            TextureRegion glyph = glyphs[c];
            batch.draw(glyph, x, y, letterWidth, letterHeight);
            x += letterWidth;
        }
    }

    public void drawText(SpriteBatch batch, String text, Rectangle r, float letterWidth, float letterHeight){
        drawText(batch, text, r.x+(r.width/2)-((text.length()*letterWidth)/2), r.y+r.height/2-letterHeight/2, letterWidth, letterHeight);
    }
}
