package net.minecraft.nbt;

import net.minecraft.util.Mth;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class FloatTag extends NumericTag {
   private static final int SELF_SIZE_IN_BYTES = 12;
   public static final FloatTag ZERO = new FloatTag(0.0F);
   public static final TagType<FloatTag> TYPE = new TagType.StaticSize<FloatTag>() {
      public FloatTag load(DataInput p_128590_, NbtAccounter p_128592_) throws IOException {
         return FloatTag.valueOf(readAccounted(p_128590_, p_128592_));
      }

      public StreamTagVisitor.ValueResult parse(DataInput p_197470_, StreamTagVisitor p_197471_, NbtAccounter p_301700_) throws IOException {
         return p_197471_.visit(readAccounted(p_197470_, p_301700_));
      }

      private static float readAccounted(DataInput p_301735_, NbtAccounter p_301757_) throws IOException {
         p_301757_.accountBytes(12L);
         return p_301735_.readFloat();
      }

      public int size() {
         return 4;
      }

      public String getName() {
         return "FLOAT";
      }

      public String getPrettyName() {
         return "TAG_Float";
      }

      public boolean isValue() {
         return true;
      }
   };
   private final float data;

   private FloatTag(float p_128564_) {
      this.data = p_128564_;
   }

   public static FloatTag valueOf(float p_128567_) {
      return p_128567_ == 0.0F ? ZERO : new FloatTag(p_128567_);
   }

   public void write(DataOutput p_128569_) throws IOException {
      p_128569_.writeFloat(this.data);
   }

   public int sizeInBytes() {
      return 12;
   }

   public byte getId() {
      return 5;
   }

   public TagType<FloatTag> getType() {
      return TYPE;
   }

   public FloatTag copy() {
      return this;
   }

   public boolean equals(Object p_128578_) {
      if (this == p_128578_) {
         return true;
      } else {
         return p_128578_ instanceof FloatTag && this.data == ((FloatTag)p_128578_).data;
      }
   }

   public int hashCode() {
      return Float.floatToIntBits(this.data);
   }

   public void accept(TagVisitor p_177866_) {
      p_177866_.visitFloat(this);
   }

   public long getAsLong() {
      return (long)this.data;
   }

   public int getAsInt() {
      return Mth.floor(this.data);
   }

   public short getAsShort() {
      return (short)(Mth.floor(this.data) & '\uffff');
   }

   public byte getAsByte() {
      return (byte)(Mth.floor(this.data) & 255);
   }

   public double getAsDouble() {
      return (double)this.data;
   }

   public float getAsFloat() {
      return this.data;
   }

   public Number getAsNumber() {
      return this.data;
   }

   public StreamTagVisitor.ValueResult accept(StreamTagVisitor p_197468_) {
      return p_197468_.visit(this.data);
   }
}