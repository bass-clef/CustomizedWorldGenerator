package jp.humibassclef

import net.minecraft.server.v1_14_R1.*
import java.util.*
import java.util.function.Predicate


public class DummyGeneratorAccess : GeneratorAccess {
    companion object {
        val INSTANCE: GeneratorAccess = DummyGeneratorAccess()
    }

    public override fun getSeed() : Long {
        return 0L//Bukkit.getWorld("world")!!.seed
    }

    public override fun getBlockTickList() : TickList<Block> {
        throw UnsupportedOperationException("Not supported yet.")
    }

    public override fun getFluidTickList() : TickList<FluidType> {
        throw UnsupportedOperationException("Not supported yet.")
    }

    public override fun getMinecraftWorld(): WorldServer {
        throw UnsupportedOperationException("Not supported yet.")
    }

    public override fun getWorldData(): WorldData {

        throw UnsupportedOperationException("Not supported yet.")
    }

    public override fun getDamageScaler(p0: BlockPosition?): DifficultyDamageScaler {
        throw UnsupportedOperationException("Not supported yet.")
    }

    public override fun getChunkProvider(): IChunkProvider {
        throw UnsupportedOperationException("Not supported yet.")
    }

    public override fun getRandom(): Random {
        throw UnsupportedOperationException("Not supported yet.")
    }

    public override fun update(p0: BlockPosition?, p1: Block?) {
        throw UnsupportedOperationException("Not supported yet.")
    }

    public override fun a(p0: EntityHuman?, p1: BlockPosition?, p2: SoundEffect?, p3: SoundCategory?, p4: Float, p5: Float) {
        throw UnsupportedOperationException("Not supported yet.")
    }

    public override fun addParticle(p0: ParticleParam?, p1: Double, p2: Double, p3: Double, p4: Double, p5: Double, p6: Double) {
        throw UnsupportedOperationException("Not supported yet.")
    }

    public override fun a(p0: EntityHuman?, p1: Int, p2: BlockPosition?, p3: Int) {
        throw UnsupportedOperationException("Not supported yet.")
    }

    public override fun getEntities(p0: Entity?, p1: AxisAlignedBB?, p2: Predicate<in Entity>?): MutableList<Entity> {
        throw UnsupportedOperationException("Not supported yet.")
    }

    public override fun <T : Entity?> a(p0: Class<out T>?, p1: AxisAlignedBB?, p2: Predicate<in T>?): MutableList<T> {
        throw UnsupportedOperationException("Not supported yet.")
    }

    public override fun getPlayers(): MutableList<out EntityHuman> {
        throw UnsupportedOperationException("Not supported yet.")
    }

    public override fun getLightLevel(p0: BlockPosition?, p1: Int): Int {
        throw UnsupportedOperationException("Not supported yet.")
    }

    public override fun getChunkAt(p0: Int, p1: Int, p2: ChunkStatus?, p3: Boolean): IChunkAccess? {
        throw UnsupportedOperationException("Not supported yet.")
    }

    public override fun getHighestBlockYAt(p0: HeightMap.Type?, p1: BlockPosition?): BlockPosition {
        throw UnsupportedOperationException("Not supported yet.")
    }

    public override fun a(p0: HeightMap.Type?, p1: Int, p2: Int): Int {
        throw UnsupportedOperationException("Not supported yet.")
    }

    public override fun c(): Int {
        throw UnsupportedOperationException("Not supported yet.")
    }

    public override fun getWorldBorder(): WorldBorder {
        throw UnsupportedOperationException("Not supported yet.")
    }

    //    public override fun a(bp: BlockPosition, ed: EnumDirection): Int {
//        throw UnsupportedOperationException("Not supported yet.")
//    }
//
    public override fun e(): Boolean {
        throw UnsupportedOperationException("Not supported yet.")
    }

    public override fun getSeaLevel(): Int {
        throw UnsupportedOperationException("Not supported yet.")
    }

    public override fun getWorldProvider(): WorldProvider {
        throw UnsupportedOperationException("Not supported yet.")
    }

    public override fun getBiome(p0: BlockPosition): BiomeBase {
        throw UnsupportedOperationException("Not supported yet.")
    }

    public override fun getBrightness(p0: EnumSkyBlock, p1: BlockPosition?): Int {
        throw UnsupportedOperationException("Not supported yet.")
    }

    public override fun getTileEntity(p0: BlockPosition): TileEntity? {
        throw UnsupportedOperationException("Not supported yet.")
    }

    public override fun getType(p0: BlockPosition): IBlockData {
        throw UnsupportedOperationException("Not supported yet.")
    }

    public override fun getFluid(p0: BlockPosition): Fluid {
        throw UnsupportedOperationException("Not supported yet.")
    }

    public override fun a(p0: BlockPosition, p1: Predicate<IBlockData>): Boolean {
        throw UnsupportedOperationException("Not supported yet.")
    }

    public override fun setTypeAndData(p0: BlockPosition?, p1: IBlockData?, p2: Int): Boolean {
        throw UnsupportedOperationException("Not supported yet.")
    }

    public override fun a(p0: BlockPosition?, p1: Boolean): Boolean {
        throw UnsupportedOperationException("Not supported yet.")
    }

    public override fun b(p0: BlockPosition?, p1: Boolean): Boolean {
        throw UnsupportedOperationException("Not supported yet.")
    }

}

/*
public class DummyGeneratorAccess(worldserver: WorldServer, list: List<IChunkAccess>) : RegionLimitedWorldAccess(worldserver, list) {
    companion object {
        var chunks: List<IChunkAccess>
        var INSTANCE: GeneratorAccess
        init {
            this.chunks = ArrayList<IChunkAccess>(1).toList()
            this.INSTANCE = DummyGeneratorAccess(
                    (Bukkit.getWorld("world") as CraftWorld).handle, chunks
            )
        }
    }

    public override fun getSeed() : Long {
        return super.getSeed()
    }

    public override fun getBlockTickList() : TickList<Block> {
        return super.getBlockTickList()
    }

    public override fun getFluidTickList() : TickList<FluidType> {
        return super.getFluidTickList()
    }

    public override fun getMinecraftWorld(): WorldServer {
        return super.getMinecraftWorld()
    }

    public override fun getWorldData(): WorldData {
        return super.getWorldData()
    }

    public override fun getDamageScaler(p0: BlockPosition?): DifficultyDamageScaler {
        return getDamageScaler(p0)
    }

    public override fun getChunkProvider(): IChunkProvider {
        return super.getChunkProvider()
    }

    public override fun getRandom(): Random {
        return super.getRandom()
    }

    public override fun update(p0: BlockPosition?, p1: Block?) {
        super.update(p0, p1)
    }

    public override fun a(p0: EntityHuman?, p1: BlockPosition?, p2: SoundEffect?, p3: SoundCategory?, p4: Float, p5: Float) {
        return super.a(p0, p1, p2, p3, p4, p5)
    }

    public override fun addParticle(p0: ParticleParam?, p1: Double, p2: Double, p3: Double, p4: Double, p5: Double, p6: Double) {
        super.addParticle(p0, p1, p2, p3, p4, p5, p6)
    }

    public override fun a(p0: EntityHuman?, p1: Int, p2: BlockPosition?, p3: Int) {
        super.a(p0, p1, p2, p3)
    }

    public override fun getEntities(p0: Entity?, p1: AxisAlignedBB?, p2: Predicate<in Entity>?): MutableList<Entity> {
        return super.getEntities(p0, p1, p2)
    }

    public override fun <T : Entity?> a(p0: Class<out T>?, p1: AxisAlignedBB?, p2: Predicate<in T>?): MutableList<T> {
        return super.a(p0, p1, p2)
    }

    public override fun getPlayers(): MutableList<out EntityHuman> {
        return super.getPlayers()
    }

    public override fun getLightLevel(p0: BlockPosition?, p1: Int): Int {
        return super.getLightLevel(p0, p1)
    }

    public override fun getChunkAt(p0: Int, p1: Int, p2: ChunkStatus?, p3: Boolean): IChunkAccess? {
        return super.getChunkAt(p0, p1, p2, p3)
    }

    public override fun getHighestBlockYAt(p0: HeightMap.Type?, p1: BlockPosition?): BlockPosition {
        return super.getHighestBlockYAt(p0, p1)
    }

    public override fun a(p0: HeightMap.Type?, p1: Int, p2: Int): Int {
        return super.a(p0, p1, p2)
    }

    public override fun c(): Int {
        return super.c()
    }

    public override fun getWorldBorder(): WorldBorder {
        return super.getWorldBorder()
    }

    //    public override fun a(bp: BlockPosition, ed: EnumDirection): Int {
//        throw UnsupportedOperationException("Not supported yet.")
//    }
//
    public override fun e(): Boolean {
        return super.e()
    }

    public override fun getSeaLevel(): Int {
        return super.getSeaLevel()
    }

    public override fun getWorldProvider(): WorldProvider {
        return super.getWorldProvider()
    }

    public override fun getBiome(p0: BlockPosition): BiomeBase {
        return super.getBiome(p0)
    }

    public override fun getBrightness(p0: EnumSkyBlock, p1: BlockPosition?): Int {
        return super.getBrightness(p0, p1)
    }

    public override fun getTileEntity(p0: BlockPosition): TileEntity? {
        return super.getTileEntity(p0)
    }

    public override fun getType(p0: BlockPosition): IBlockData {
        return super.getType(p0)
    }

    public override fun getFluid(p0: BlockPosition): Fluid {
        return super.getFluid(p0)
    }

    public override fun a(p0: BlockPosition, p1: Predicate<IBlockData>): Boolean {
        return super.a(p0, p1)
    }

    public override fun setTypeAndData(p0: BlockPosition?, p1: IBlockData?, p2: Int): Boolean {
        return super.setTypeAndData(p0, p1, p2)
    }

    public override fun a(p0: BlockPosition?, p1: Boolean): Boolean {
        return super.a(p0, p1)
    }

    public override fun b(p0: BlockPosition?, p1: Boolean): Boolean {
        return super.b(p0, p1)
    }

}
*/
